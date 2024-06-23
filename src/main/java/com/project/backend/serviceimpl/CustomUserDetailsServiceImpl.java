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
import com.project.backend.service.AccountService;
import com.project.backend.service.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Optional<Account> account = accountService.findByUserEmail(userEmail);
		if (account.isPresent()) {
			System.out.println(account);
			Account user = account.get();
			List<Role> roles = user.getRoles();

			List<GrantedAuthority> grantedAuthorities = roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());

			return User.withUsername(user.getUserEmail()).password(user.getUserPassword())
					.authorities(grantedAuthorities).build();
		} else {
			throw new UsernameNotFoundException("User Not Founded in Email " + userEmail);
		}

	}
}
