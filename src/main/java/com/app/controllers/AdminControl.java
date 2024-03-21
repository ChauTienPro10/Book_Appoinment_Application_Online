package com.app.controllers;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.Doctor;
import com.app.entites.User;
import com.app.repositories.UserRepository;
import com.app.services.DoctorService;
import com.app.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminControl {
	UserService userService;
	DoctorService doctorService;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public AdminControl(UserService userService, DoctorService doctorService) {
		super();
		this.userService = userService;
		this.doctorService = doctorService;
	}
	
	@PostMapping("/addDr")
	public ResponseEntity<Doctor> addnewDoctor(@RequestBody Map<String, String> jsonData) {
		try {
			String email=jsonData.get("email");
			User us=userService.findUserByMail(email);
			Doctor dr=new Doctor();
			dr.setUserid(us.getUserid());
			dr.setSpecialization(jsonData.get("specialization"));
			dr.setName(us.getName());
			Doctor newdr=doctorService.AddNewDoctor(dr);
			if(newdr!=null) {
				us.setRole("DOCTOR");
				userRepository.save(us);
				return ResponseEntity.ok(newdr);
			}
			else {
				return ResponseEntity.notFound().build();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
