package com.aurora.raisedline.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.aurora.raisedline.entities.User;

public class SignupForm {

	@NotNull
	@Size(min=1, max=255)
	@Pattern(regexp=User.EMAIL_PATTERN,
				message="{emailPatternError}")
	private String email;
	
	@NotNull
	@Size(min=1, max=55,
			message="{nameSizeError}")
	private String name;
	
	@NotNull
	@Size(min=1, max=1024)
	private String password;
	
	@NotNull
	@Size(min=1, max=1024)
	private String verifyPassword;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	@Override
	public String toString() {
		return "SignupForm [email=" + email + ", name=" + name + ", password=" + password + "]";
	}
	
}
