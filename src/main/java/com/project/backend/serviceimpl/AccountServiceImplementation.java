package com.project.backend.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.entity.Account;
import com.project.backend.repository.AccountRepository;
import com.project.backend.service.AccountService;

@Service
public class AccountServiceImplementation implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account registerUser(Account account) {
		return accountRepository.save(account);
	}
	
	@Override
	public Optional<Account> findByUserEmail(String userEmail) {
		return accountRepository.findByUserEmail(userEmail);
	}
}
