package com.app.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import com.app.JwtTokenProvider;
import com.app.entites.CustomUserDetails;
import com.app.entites.EmailDetails;
import com.app.entites.User;

import com.app.loginHttp.LoginRequest;
import com.app.loginHttp.LoginResponse;
import com.app.repositories.EmailService;
import com.app.repositories.UserRepository;
import com.app.services.UserService;

@RestController()
@RequestMapping("/api")
public class TestControler {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	private JwtTokenProvider tokenProvider;

//	@PostMapping("/login")
//	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
//			throws AuthenticationException {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
//		@SuppressWarnings("unchecked")
//		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
//		User us = userRepository.findByUsername(loginRequest.getUsername());
//		return new LoginResponse(jwt, authorities, authentication.getName(), us.getUserid());
//	}
	
	@PostMapping("/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
	        HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    // Tạo CSRF token và đặt vào cookie
	    CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", request.getAttribute(CsrfToken.class.getName()).toString());
	    Cookie cookie = new Cookie(csrfToken.getParameterName(), csrfToken.getToken());
	    cookie.setPath("/");
	    response.addCookie(cookie);

	    // Tiếp tục xử lý và tạo JWT token
	    String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
	    @SuppressWarnings("unchecked")
	    List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
	    User us = userRepository.findByUsername(loginRequest.getUsername());
	    return new LoginResponse(jwt, authorities, authentication.getName(), us.getUserid());
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("Logged out successfully");
	}
	
	@PermitAll
	@SuppressWarnings("unused")
	@PostMapping("/register")
	public ResponseEntity<User> addnewuserContrl(@RequestBody Map<String, String> jsonData) {
		String username = jsonData.get("username");
		if(userRepository.findByUsername(username)!=null) {
			return ResponseEntity.notFound().build();
	 	}
		String password=jsonData.get("password");
		if(password.length()>10 || password.length()<6) {
			return ResponseEntity.notFound().build();
		}
		String email=jsonData.get("email");
		if(userService.findUserByMail(email)!=null) {
			return ResponseEntity.notFound().build();
		}
		String role=jsonData.get("role");
		String name=jsonData.get("name");
		User newUs=new User(username,password,email,role,name);
		if(newUs !=null) {
			try {
				return ResponseEntity.ok(userService.addNewUser(newUs));
			}
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}


	@PostMapping("/random")
	public String randomStuff() {
		return ("JWT Hợp lệ mới có thể thấy được message này");
	}

	

	@PostMapping("/check")
	public ResponseEntity<UserDetails> LoginByUsernameAndPassword(@RequestBody Map<String, String> jsonData)
			throws NoSuchAlgorithmException {
		String username = jsonData.get("username");
		UserDetails us = userService.loadUserByUsername(username);
		return ResponseEntity.ok(us);
	}

	@GetMapping("/authentication")
	public List<GrantedAuthority> getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
		return authorities;
	}
	
	
	@Autowired private EmailService emailService;
	 
	@PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
		try {
			String status
            = emailService.sendSimpleMail(details);
 
        return status;
		}
		catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
        
    }
	@PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
        @RequestBody EmailDetails details)
    {
        String status
            = emailService.sendMailWithAttachment(details);
 
        return status;
    }

}
