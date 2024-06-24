package com.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.backend.dto.AccountCreatingDto;
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

	@GetMapping("/")
	public String userHomePage(@AuthenticationPrincipal User user, Model model) {
		if (user != null) {
			model.addAttribute("authentication", user);
			model.addAttribute("isAdmin", user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
		}
		return "home";
	}

	@GetMapping("/admin/")
	public String adminHomePage(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("authentication", user);
		model.addAttribute("hasUserRole", user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
		return "adminHome";
	}

	@GetMapping("/user/register")
	public String userRegisterPage(AccountCreatingDto accountCreatingDto, Model model) {
		model.addAttribute("accountCreateDto", accountCreatingDto);
		return "signUpPage";
	}

	@PostMapping("/user/register")
	public String registerUser(@Valid @ModelAttribute AccountCreatingDto accountCreatingDto,
			BindingResult bindingResult, Model model, Role role, Account account) {
		if (bindingResult.hasErrors()) {
			return "signUpPage";
		}
		account.setUserName(accountCreatingDto.getUserName());
		account.setUserEmail(accountCreatingDto.getUserEmail());
		account.setUserContact(accountCreatingDto.getUserContact());
		account.setUserPassword(passwordEncoder.encode(accountCreatingDto.getUserPassword()));
		account.getRoles().add(role.ROLE_USER);
		accountService.registerUser(account);
		return "redirect:/login";
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
	public String loginPage() {
		return "loginPage";
	}

}
