package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.Doctor;
import com.app.services.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorContrl {
	
	private DoctorService doctorService;
	@Autowired
	public DoctorContrl(DoctorService doctorService) {
		super();
		this.doctorService = doctorService;
	}
	
	@GetMapping("/getDoctor")
	public ResponseEntity<List<Doctor>> GetAllDoctor(){
		List<Doctor> drList=doctorService.getAllDoctor();
		return ResponseEntity.ok(drList);
	}
	
	
}
