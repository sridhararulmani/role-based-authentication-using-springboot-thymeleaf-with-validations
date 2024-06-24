package com.project.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.project.backend.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final UserDetailsService userDetailsService;

	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfiguration(UserDetailsService userDetailsService,
			CustomUserDetailsService customUserDetailsService) {
		this.userDetailsService = userDetailsService;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(register -> {
			register.requestMatchers("/user/register","/").permitAll();
			register.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN");
			register.requestMatchers("/admin/**").hasRole("ADMIN");
			register.anyRequest().authenticated();
		}).formLogin(formLogin -> {
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/perform_login");
			formLogin.successHandler(customAuthenticationSuccessHandler());
			formLogin.failureUrl("/login?error=true");
			formLogin.permitAll();
		}).logout(logoutForm -> {
			logoutForm.logoutSuccessUrl("/");
			logoutForm.logoutUrl("/perform_logout");
		}).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

}