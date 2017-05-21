package com.aurora.raisedline.services;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindingResult;

import com.aurora.raisedline.controllers.RootController;
import com.aurora.raisedline.dto.ForgotPasswordForm;
import com.aurora.raisedline.dto.OrderForm;
import com.aurora.raisedline.dto.ResetPasswordForm;
import com.aurora.raisedline.dto.SignupForm;
import com.aurora.raisedline.dto.UserDetailsImpl;
import com.aurora.raisedline.dto.UserEditForm;
import com.aurora.raisedline.entities.Person;
import com.aurora.raisedline.entities.User;
import com.aurora.raisedline.entities.User.Role;
import com.aurora.raisedline.mail.MailSender;
import com.aurora.raisedline.repositories.PersonRepository;
import com.aurora.raisedline.repositories.UserRepository;
import com.aurora.raisedline.util.RaisedLineUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);

	private UserRepository userRepository;
	private PersonRepository personRepository;
	private PasswordEncoder passwordEncoder;
	private MailSender mailSender;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, 
			MailSender mailSender, PersonRepository personRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
		this.personRepository = personRepository;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void signup(SignupForm signupForm) {
		if (signupForm.getPassword().equals(signupForm.getVerifyPassword())){ //verification!!
			final User user = new User();
			user.setEmail(signupForm.getEmail());
			user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
			user.getRoles().add(Role.UNVERIFIED);
			user.setVerificationCode(RandomStringUtils.randomAlphanumeric(User.RANDOM_CODE_LENGTH));
			userRepository.save(user);
			TransactionSynchronizationManager.registerSynchronization(
				new TransactionSynchronizationAdapter() {
					@Override
					public void afterCommit(){
						try {
							String verifyLink = RaisedLineUtil.hostUrl() + "/users/" + user.getVerificationCode() + "/verify";
							mailSender.send(user.getEmail(), RaisedLineUtil.getMessage("verifySubject"), 
									RaisedLineUtil.getMessage("verifyEmail", verifyLink));
						} catch (MessagingException e) {
							logger.error(ExceptionUtils.getStackTrace(e));
							logger.info("Verification mail to " + user.getEmail() + " is queued");
						}
					}
			});
		}
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null){
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void verify(String verificationCode){
		long loggedInUserId = RaisedLineUtil.getSessionUser().getId();
		User user = userRepository.findOne(loggedInUserId);
		RaisedLineUtil.validate(user.getRoles().contains(Role.UNVERIFIED), "alreadyVerified");
		RaisedLineUtil.validate(user.getVerificationCode().equals(verificationCode), "incorrect", 
				"verification code");
		user.getRoles().remove(Role.UNVERIFIED);
		user.setVerificationCode(null);
		userRepository.save(user);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void forgotPassword(ForgotPasswordForm form) {
		
		final User user = userRepository.findByEmail(form.getEmail());
		final String forgotPasswordCode = RandomStringUtils.randomAlphanumeric(User.RANDOM_CODE_LENGTH);
		
		user.setForgotPasswordCode(forgotPasswordCode);
		final User savedUser = userRepository.save(user);
		
		TransactionSynchronizationManager.registerSynchronization(
		    new TransactionSynchronizationAdapter() {
		        @Override
		        public void afterCommit() {
		        	try {
						mailForgotPasswordLink(savedUser);
					} catch (MessagingException e) {
						logger.error(ExceptionUtils.getStackTrace(e));
					}
		        }
	    });				
	}


	protected void mailForgotPasswordLink(User user) throws MessagingException {
		String forgotPasswordLink = RaisedLineUtil.hostUrl() + "/reset-password/" + user.getForgotPasswordCode();
		mailSender.send(user.getEmail(), RaisedLineUtil.getMessage("forgotPasswordSubject"),
				RaisedLineUtil.getMessage("forgotPasswordEmail", forgotPasswordLink));
		
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void resetPassword(String forgotPasswordCode, ResetPasswordForm resetPasswordForm, BindingResult result) {
		
		User user = userRepository.findByForgotPasswordCode(forgotPasswordCode);
		if (user == null){
			result.reject("invalidForgotPassword");
		}
		
		if (result.hasErrors()){
			return;
		}
		
		user.setForgotPasswordCode(null);
		user.setPassword(passwordEncoder.encode(resetPasswordForm.getPassword().trim()));
		userRepository.save(user);

	}


	@Override
	public User findOne(long userId) {
		User loggedIn = RaisedLineUtil.getSessionUser();
		
		User user = userRepository.findOne(userId);
		if (loggedIn == null ||
				loggedIn.getId() != user.getId() && loggedIn.isAdmin()){
			user.setEmail("Confidential");
		}
		return user;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(long userId, UserEditForm userEditForm) {
		User loggedIn = RaisedLineUtil.getSessionUser();
		RaisedLineUtil.validate(loggedIn.isAdmin() || loggedIn.getId() == userId, "noPermission");
		User user = userRepository.findOne(userId);
		user.setName(userEditForm.getName());
		if (loggedIn.isAdmin()){
			user.setRoles(userEditForm.getRoles());
		}
		userRepository.save(user);
	}
	/*
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public User registerReturnUser(OrderForm orderForm){
		final Person person = new Person();
		person.setAddressLine1(orderForm.getAddressLine1());
		person.setAddressLine2(orderForm.getAddressLine2());
		person.setFirstName(orderForm.getFirstName());
		person.setLastName(orderForm.getLastName());
		person.setProvince(orderForm.getProvince());
		personRepository.save(person);
		final User user = new User();
		user.setEmail(orderForm.getEmail());
		user.setName(orderForm.getUserName());
		user.setPassword(passwordEncoder.encode(orderForm.getPassword()));
		user.getRoles().add(Role.UNVERIFIED);
		user.setVerificationCode(RandomStringUtils.randomAlphanumeric(User.RANDOM_CODE_LENGTH));
		user.setPerson(person);
		userRepository.save(user);
		TransactionSynchronizationManager.registerSynchronization(
			new TransactionSynchronizationAdapter() {
				@Override
				public void afterCommit(){
					try {
						String verifyLink = RaisedLineUtil.hostUrl() + "/users/" + user.getVerificationCode() + "/verify";
						mailSender.send(user.getEmail(), RaisedLineUtil.getMessage("verifySubject"), 
								RaisedLineUtil.getMessage("verifyEmail", verifyLink));
					} catch (MessagingException e) {
						logger.error(ExceptionUtils.getStackTrace(e));
						logger.info("Verification mail to " + user.getEmail() + " is queued");
					}
				}
		});
		return user;
	}*/
}
