package com.aurora.raisedline.controllers;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aurora.raisedline.dto.ForgotPasswordForm;
import com.aurora.raisedline.dto.ResetPasswordForm;
import com.aurora.raisedline.dto.SignupForm;
import com.aurora.raisedline.mail.MailSender;
import com.aurora.raisedline.services.UserService;
import com.aurora.raisedline.util.RaisedLineUtil;
import com.aurora.raisedline.validators.ForgotPasswordFormValidator;
import com.aurora.raisedline.validators.ResetPasswordFormValidator;
import com.aurora.raisedline.validators.SignupFormValidator;

@Controller
public class RootController {
	
	private UserService userService;
	private SignupFormValidator signupFormValidator;
	private ForgotPasswordFormValidator forgotPasswordFormValidator;
	private ResetPasswordFormValidator resetPasswordFormValidator;
	private MailSender mailSender;
	
	@Autowired
	public RootController(MailSender mailSender, UserService userService,
			SignupFormValidator signupFormValidator, ForgotPasswordFormValidator forgotPasswordFormValidator,
			ResetPasswordFormValidator resetPasswordFormValidator) {
		this.mailSender = mailSender;
		this.userService = userService;
		this.signupFormValidator = signupFormValidator;
		this.forgotPasswordFormValidator = forgotPasswordFormValidator;
		this.resetPasswordFormValidator = resetPasswordFormValidator;
	}
	
	@InitBinder("signupForm")
	protected void initSignupBinder(WebDataBinder binder){
		binder.setValidator(signupFormValidator);
	}
	
	@InitBinder("resetPasswordForm")
	protected void initResetPasswordFormBinder(WebDataBinder binder){
		binder.setValidator(resetPasswordFormValidator);
	}
	
	@InitBinder("forgotPasswordForm")
	protected void initForgotPasswordFormBinder(WebDataBinder binder){
		binder.setValidator(forgotPasswordFormValidator);
	}
	
	@RequestMapping(value="/")
	public String goHome(){
		return "redirect:/home";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String home(Model model) throws MessagingException {
		model.addAttribute(new SignupForm());
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST )
	public String signup(@ModelAttribute("signupForm") @Valid SignupForm signupForm, BindingResult br,
			RedirectAttributes redirectAttributes){
		if (br.hasErrors()){
			return "signup";
		}
		userService.signup(signupForm);
		RaisedLineUtil.flash(redirectAttributes, "success", "signupSuccess");
		return "redirect:/";
	}
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public String forgotPassword(Model model){
		model.addAttribute(new ForgotPasswordForm());
		return "forgot-password";
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String forgotPassword(
			@ModelAttribute("forgotPasswordForm") @Valid ForgotPasswordForm forgotPasswordForm,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return "forgot-password";

		userService.forgotPassword(forgotPasswordForm);
		RaisedLineUtil.flash(redirectAttributes, "info", "checkMailResetPassword");

		return "redirect:/";
	}
	
	@RequestMapping(value="/reset-password/{forgotPasswordCode}")
	public String resetPassword(@PathVariable("forgotPasswordCode") String forgotPasswordCode, Model model){
		model.addAttribute(new ResetPasswordForm());
		return "reset-password";
		
	}
	
	@RequestMapping(value="reset-password/{forgotPasswordCode}", method=RequestMethod.POST)
	public String resetPassword(@PathVariable("forgotPasswordCode") String forgotPasswordCode, 
			@ModelAttribute("resetPasswordForm") @Valid ResetPasswordForm resetPasswordForm,
			BindingResult result,
			RedirectAttributes redirectAttributes){
		userService.resetPassword(forgotPasswordCode, resetPasswordForm, result);
		
		if (result.hasErrors()){
			return "reset-password";
		}
		
		RaisedLineUtil.flash(redirectAttributes, "success", "passwordChanged");
	
		return "redirect:/";
	}
	
}
