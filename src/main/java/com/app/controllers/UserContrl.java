package com.app.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.User;
import com.app.entites.UserResponse;
import com.app.services.UserService;


@RestController
@RequestMapping("/user")
public class UserContrl {
	
	private UserService userService;
	
	@Autowired
	public UserContrl(UserService userService) {
		super();
		this.userService = userService;
	}
	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	@GetMapping("/test")
	public String getTest() {
		return "hello";
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> LoginByUsernameAndPassword(
    		@RequestBody Map<String, String> jsonData, HttpSession session
    ) throws NoSuchAlgorithmException {
	 	String username = jsonData.get("username");
	 	
	    String password = jsonData.get("password");
	    System.out.print(username);
	    System.out.print(password);
        User us = userService.findUserByUsernameAndPassword(username, password);
        if (us != null) {
        	UserResponse usResp=new UserResponse("user","12345","");
            if(us.getRole().equals("USER")) {
            	usResp.setRole("USER");
            }
            else if(us.getRole().equals("ADMIN")) {
            	usResp.setRole("ADMIN");
            }
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("role", usResp.getRole());
            return ResponseEntity.ok(usResp);
           
        } else {
            return ResponseEntity.notFound().build();
        	
        }
    }
	
	@GetMapping("/logout")
	  public String logout(HttpSession session) {
	    // Xóa thông tin người dùng khỏi HttpSession để đăng xuất


	    return (String) session.getAttribute("username");
	  }
//	@SuppressWarnings("unused")
//	@PostMapping("/add")
//	public ResponseEntity<User> addnewuserContrl(@RequestBody Map<String, String> jsonData) {
//		String username = jsonData.get("username");
//		if(userService.loadUserByUsername(username)!=null) {
//			return ResponseEntity.notFound().build();
//	 	}
//		String password=jsonData.get("password");
//		if(password.length()>10 || password.length()<6) {
//			return ResponseEntity.notFound().build();
//		}
//		String email=jsonData.get("email");
//		if(userService.findUserByMail(email)!=null) {
//			return ResponseEntity.notFound().build();
//		}
//		String role=jsonData.get("role");
//		String name=jsonData.get("name");
//		User newUs=new User(username,password,email,role,name);
//		if(newUs !=null) {
//			try {
//				return ResponseEntity.ok(userService.addNewUser(newUs));
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				return ResponseEntity.notFound().build();
//			}
//		}
//		return ResponseEntity.notFound().build();
//	}
	@PostMapping("/del")
	public boolean deleteUser(HttpServletRequest httpRequest) {
		int id=Integer.parseInt( httpRequest.getParameter("id"));
		try {
			userService.deleteUser(id);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
