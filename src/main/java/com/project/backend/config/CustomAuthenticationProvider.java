package com.project.backend.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.backend.service.CustomUserDetailsService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final CustomUserDetailsService customUserDetailsService;

	public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService,
			PasswordEncoder passwordEncoder) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userEmail = authentication.getName();
		String userPassword = authentication.getCredentials().toString();

		UserDetails user = customUserDetailsService.loadUserByUsername(userEmail);
		
		if (!passwordEncoder().matches(userPassword, user.getPassword())) {
			throw new BadCredentialsException("Invalid User email or Password");
		}
		return new UsernamePasswordAuthenticationToken(user, userPassword, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
