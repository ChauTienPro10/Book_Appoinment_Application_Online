package com.app.controllers;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
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

import com.app.JwtTokenProvider;
import com.app.entites.CustomUserDetails;
import com.app.entites.User;
import com.app.entites.UserResponse;
import com.app.loginHttp.LoginRequest;
import com.app.loginHttp.LoginResponse;
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
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public LoginResponse authenticateUser( @Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {

        
        	Authentication authentication = authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
			);
			// Xác thực thành công
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
			@SuppressWarnings("unchecked")
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
			User us=userRepository.findByUsername(loginRequest.getUsername());
	        return new LoginResponse(jwt,authorities,authentication.getName(),us.getUserid());
        
        
    }


 
    @PostMapping("/random")
    public String randomStuff(){
        return ("JWT Hợp lệ mới có thể thấy được message này");
    }
    
    @Autowired
    UserService userService;
    
    @PostMapping("/check")
	public ResponseEntity<UserDetails> LoginByUsernameAndPassword(
    		@RequestBody Map<String, String> jsonData
    ) throws NoSuchAlgorithmException {
	 	String username = jsonData.get("username");
	    UserDetails us= userService.loadUserByUsername(username);
	    return ResponseEntity.ok(us);
    }
    
    

    @GetMapping("/authentication")
    public List<GrantedAuthority> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        @SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        return authorities;
    }
    
    
   
	
}
