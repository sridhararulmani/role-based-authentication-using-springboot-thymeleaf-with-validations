package com.project.backend.dto;

import com.project.backend.validation.UniqueData;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountCreatingDto {

	@Size(min = 3, message = "User Name should be have atleast 3 characters")
	@Size(max = 25, message = "User Name should contains less then 25 characters")
	@UniqueData(message = "This User Name is Not Available\n" + "Please Try to Set different User Name")
	private String userName;

	@Email(message = "Invalid email", regexp = "^(?=.*\\d{3,})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
	@UniqueData(message = "This Email is already have an account\n" + "Pleas try again with Different valid Email")
	private String userEmail;

	@NotBlank(message = "Password is Mandatory")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Invalid Password.\n"
			+ "1.Password should have ateast 8 alphabatics\n" + "2.Password should have 1 uper Camal case\n"
			+ "3.Password 1 special character 3 numbers.")
	private String userPassword;

	@Digits(integer = 10, fraction = 0, message = "Invalid contact number\n"
			+ "1.Contact number should have 10 digits\n" + "2.Contact number should not consit any decimal values")
	private Long userContact;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

	public Long getUserContact() {
		return userContact;
	}

	public void setUserContact(Long userContact) {
		this.userContact = userContact;
	}

}
