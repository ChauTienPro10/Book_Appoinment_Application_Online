package com.app.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Map;





import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.Account;
import com.app.entites.UserResponse;
import com.app.services.AccountService;


@RestController
@RequestMapping("/account")
public class AccountControler {
	AccountService accountService;



	@Autowired
	public AccountControler(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@PostMapping("/hello")
	public String PostHello() {

		return "hello";
	}
	@GetMapping("/hello")
	public String GetHello() {

		return "hello";
	}
	

	@PostMapping("/modelogin")
	public ResponseEntity<UserResponse> LoginByUsernameAndPassword(
	    		@RequestBody Map<String, String> jsonData
	    ) throws NoSuchAlgorithmException {
		 	String username = jsonData.get("username");
		    String password = jsonData.get("password");
		    String role=jsonData.get("role");

	        Account account = accountService.getAccountByUsernameAndPassword(username, password);
	        if (account != null) {
	        	UserResponse usRes=new UserResponse();
	        	if(role.equals("USER")) {
	        		usRes.setRole("USER");
	        		usRes.setUsername("user");
	        		usRes.setPassword("123");
	        	}
	        	else if(role.equals("ADMIN")) {
	        		usRes.setRole("ADMIN");
	        		usRes.setUsername("admin");
	        		usRes.setPassword("456");
	        	}
	            return ResponseEntity.ok(usRes);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

//	@GetMapping("/check-user-role")
//	public String checkUserRole(Authentication authentication) {
//		if (authentication != null && authentication.isAuthenticated()) {
//			boolean hasUserRole = authentication.getAuthorities().stream()
//					.anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
//
//			if (hasUserRole) {
//				return "User has ROLE_USER";
//			} else {
//				return "User does not have ROLE_USER";
//			}
//		}
//
//		return "User is not authenticated";
//	}

	

}
