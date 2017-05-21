package com.aurora.raisedline.services;

import org.springframework.validation.BindingResult;

import com.aurora.raisedline.dto.ForgotPasswordForm;
import com.aurora.raisedline.dto.OrderForm;
import com.aurora.raisedline.dto.ResetPasswordForm;
import com.aurora.raisedline.dto.SignupForm;
import com.aurora.raisedline.dto.UserEditForm;
import com.aurora.raisedline.entities.User;

public interface UserService {
	public abstract void signup(SignupForm signupForm);
	public void verify(String verificationCode);
	public abstract void forgotPassword(ForgotPasswordForm forgotPasswordForm);
	public abstract void resetPassword(String forgotPasswordCode, ResetPasswordForm resetPasswordForm,
			BindingResult result);
	public abstract User findOne(long userId);
	public abstract void update(long userId, UserEditForm userEditForm);
//	public abstract User registerReturnUser(OrderForm orderForm);
}
