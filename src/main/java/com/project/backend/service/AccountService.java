package com.project.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.backend.entity.Account;

@Service
public interface AccountService {

	public Account registerUser(Account account);

	public Optional<Account> findByUserEmail(String userEmail);

}
