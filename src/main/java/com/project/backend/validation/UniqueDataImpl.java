package com.project.backend.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.backend.repository.AccountRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueDataImpl implements ConstraintValidator<UniqueData, String> {

	@Autowired
	private AccountRepository accountRepository;

//	if we return false means that @UniqueData used feild is valid for the error
	@Override
	public boolean isValid(String userEmail, ConstraintValidatorContext context) {

		if (userEmail == null) {
			return true;
		}

		return !accountRepository.existsByUserEmail(userEmail);

	}
}
