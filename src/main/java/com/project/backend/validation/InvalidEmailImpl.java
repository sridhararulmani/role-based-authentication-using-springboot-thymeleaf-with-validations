package com.project.backend.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.backend.repository.AccountRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InvalidEmailImpl implements ConstraintValidator<InvalidEmail, String> {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean isValid(String userEmail, ConstraintValidatorContext context) {
		if (userEmail == null) {
			return true;
		}
		return accountRepository.existsByUserEmail(userEmail);
	}
}
