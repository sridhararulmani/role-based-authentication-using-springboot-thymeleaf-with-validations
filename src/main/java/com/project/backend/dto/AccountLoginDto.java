package com.project.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AccountLoginDto {

	@NotBlank(message = "Email is Required to Login")
	@Email(message = "Enter the Valid Email")
	private String userEmail;

	@NotBlank(message = "Password is Required to Login")
	private String userPassword;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
