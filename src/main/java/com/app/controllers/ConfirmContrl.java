package com.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.Appointment;
import com.app.entites.Confirm;
import com.app.entites.Doctor;
import com.app.entites.User;
import com.app.loginHttp.LoginRequest;
import com.app.repositories.DoctorRepository;
import com.app.services.ConfirmService;
import com.app.services.UserService;

@RestController
@RequestMapping("/confirm")
public class ConfirmContrl {
	
	private ConfirmService confirmService;
	private UserService userService;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	public ConfirmContrl(ConfirmService confirmService,UserService userService) {
		super();
		this.confirmService = confirmService;
		this.userService=userService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Confirm> addnewCfm(@RequestBody Map<String, String> jsonData){
		try {
			String email=jsonData.get("email");
			long idDr=Integer.parseInt(jsonData.get("iddoctor"));
			if(confirmService.findDoctorById(idDr)==false) {
				return ResponseEntity.notFound().build();
			}
			User us=userService.findUserByMail(email);
			String timeofString = jsonData.get("appointmentDate");
			System.out.print(timeofString);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date timeof;
			try {
			    timeof = dateFormat.parse(timeofString);
			} catch (ParseException e) {
			    e.printStackTrace();
			    timeof = null; // or assign a default value
			}
			
			Confirm cf=new Confirm( us.getUserid(),idDr, timeof,jsonData.get("appointmentTime"));
			Confirm conf=  confirmService.AddConfirm(cf);
			return ResponseEntity.ok(conf);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping("/allConfirm")
	public ResponseEntity<List<Confirm>> GetAllDoctor(@RequestBody Map<String, String> jsonData){
		long id=Long.valueOf(jsonData.get("idDoctor"));
		Doctor dr=doctorRepository.findDoctorByUserid(id);
		List<Confirm> cfrmList= confirmService.getAllConfirm(dr.getDoctorid());
		return ResponseEntity.ok(cfrmList);
	}
	
	@PostMapping("/agree")
	public Appointment ApiAgree(@RequestBody Map<String, String> jsonData){
		try {
			Appointment appointment=confirmService.ConfirmAppoinment(Long.valueOf(jsonData.get("id")));
			return appointment;
		}
		catch (Exception e) {
			return null;
		}
	}

	
	
}
