package com.aurora.raisedline.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.aurora.raisedline.dto.ResetPasswordForm;

@Component
public class ResetPasswordFormValidator extends LocalValidatorFactoryBean {

	@Override
	public boolean supports(Class<?> clazz){
		return clazz.isAssignableFrom(ResetPasswordForm.class);
	}

	@Override
	public void validate(Object object, Errors errors, final Object... validationHints){
		super.validate(object, errors, validationHints);
		if (!errors.hasErrors()){
			ResetPasswordForm resetPasswordForm = (ResetPasswordForm) object;
			if (!resetPasswordForm.getPassword().equals(resetPasswordForm.getRetypePassword())){
				errors.reject("passwordsDoNotMatch");
			}
		}
	}
}
