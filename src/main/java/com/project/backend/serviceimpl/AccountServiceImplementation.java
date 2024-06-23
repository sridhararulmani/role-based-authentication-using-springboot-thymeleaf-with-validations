package com.project.backend.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.backend.entity.Account;
import com.project.backend.enumerate.Role;
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
