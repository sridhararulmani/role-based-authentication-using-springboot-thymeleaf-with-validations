package com.project.backend.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.backend.config.CustomAuthenticationProvider;
import com.project.backend.config.SecurityConfiguration;
import com.project.backend.dto.AccountLoginDto;
import com.project.backend.entity.Account;
import com.project.backend.enumerate.Role;
import com.project.backend.service.AccountService;

import jakarta.validation.Valid;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String userHomePage(Authentication authentication, Model model) {
		if (authentication != null) {
			model.addAttribute("authentication", authentication.getPrincipal());
			model.addAttribute("isAdmin",
					authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
		}
		return "home";
	}

	@GetMapping("/admin/")
	public String adminHomePage(Authentication authentication, Model model) {
		model.addAttribute("authentication", authentication.getPrincipal());
		model.addAttribute("hasUserRole",
				authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
		return "adminHome";
	}

	@PostMapping("/user/register")
	@ResponseBody
	public Account registerUser(@RequestBody Account account, Role role) {
		account.setUserPassword(passwordEncoder.encode(account.getUserPassword()));
		account.getRoles().add(role.ROLE_USER);
		return accountService.registerUser(account);
	}

	@PostMapping("/admin/register")
	@ResponseBody
	public Account registerAdmin(@RequestBody Account account, Role role) {
		account.setUserPassword(passwordEncoder.encode(account.getUserPassword()));
		account.getRoles().add(role.ROLE_USER);
		account.getRoles().add(role.ROLE_ADMIN);
		return accountService.registerUser(account);
	}

	@GetMapping("/login")
	public String loginPage(Model model, AccountLoginDto accountLoginDto) {
		model.addAttribute("accountLoginDto", accountLoginDto);
		return "loginPage";
	}

	@PostMapping("/login")
	public String loginAccount(@Valid @ModelAttribute(value = "accountLoginDto") AccountLoginDto accountLoginDto,
			BindingResult bindingResult, Model model, SecurityContextHolder securityContextHolder) {

		if (bindingResult.hasErrors()) {
			return "loginPage";
		}

		try {
			System.out.println("Try Block Exicuting...");
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					accountLoginDto.getUserEmail(), accountLoginDto.getUserPassword()));
			System.out.println(authenticate.isAuthenticated() + " Authendicated");
		} catch (AuthenticationException e) {
			System.out.println("Invalid Email or Password");
			model.addAttribute("InvalidEmailOrPassword", "Invalid Email or Password");
			return "loginPage";
		}

		return "redirect:/";
	}

}
