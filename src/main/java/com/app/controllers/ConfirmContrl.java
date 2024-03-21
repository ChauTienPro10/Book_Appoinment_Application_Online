package com.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entites.Appointment;
import com.app.entites.Confirm;
import com.app.entites.Doctor;
import com.app.entites.EmailDetails;
import com.app.entites.User;
import com.app.loginHttp.LoginRequest;
import com.app.repositories.AppointmentRepository;
import com.app.repositories.ConfirmRepository;
import com.app.repositories.DoctorRepository;
import com.app.repositories.EmailService;
import com.app.repositories.UserRepository;
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
	AppointmentRepository appointmentRepository;
	@Autowired
	UserRepository userRepository;
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
			    timeof = null; 
			}
			if(appointmentRepository.findAppointmentByTimeof(timeof)!=null) {
				return  ResponseEntity.notFound().build();
			}
			Confirm cf=new Confirm( us.getUserid(),idDr, timeof,jsonData.get("appointmentTime"),us.getName(),us.getEmail());
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
		try {
			long id=Long.valueOf(jsonData.get("userid"));
			System.out.println();
			System.out.println(id);
			Doctor dr=doctorRepository.findDoctorByUserid(id);
			System.out.println();
			System.out.println(dr.getDoctorid());
			List<Confirm> cfrmList= confirmService.getAllConfirm(dr.getDoctorid());
			return ResponseEntity.ok(cfrmList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		
	}
	@Autowired private EmailService emailService;
	
	@PostMapping("/agree")
	public String ApiAgree(@RequestBody List<Confirm> confirmations){
		try {
			for (Confirm confirm : confirmations) {
				
	            confirmService.ConfirmAppoinment(confirm.getId());
	            try {
	            	String mail=confirm.getMailcustomer();
	            	EmailDetails maildt=new EmailDetails(mail,"The doctor has accepted your appointment ", "congratulate");
	            	emailService.sendSimpleMail(maildt);
	            	 
	            	
	            }
	            catch (Exception e) {
					e.printStackTrace();
				}
	        }			
			return "yes";			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@PostMapping("/getAppoinment")
	public ResponseEntity<List<Appointment>> getAppointment(@RequestBody Map<String, String> jsonData){
		try {
			long id=Long.parseLong(jsonData.get("iduser"));
			System.out.println();
			System.out.println(id);
			System.out.println();
			User us=userRepository.getById(id);
			if(us.getRole().equals("DOCTOR")) {
				Doctor dr=doctorRepository.findDoctorByUserid(id);
				List<Appointment> apps=appointmentRepository.getAppointmentByDoctorid(dr.getDoctorid());
				apps.sort(Comparator.comparing(Appointment::getTimeof));
				return ResponseEntity.ok(apps);
			}
			else {
				List<Appointment> apps=appointmentRepository.getAppointmentByUserid(id);
				System.out.println();
				System.out.println(apps);
				System.out.println();
//				apps.sort(Comparator.comparing(Appointment::getTimeof));
				return ResponseEntity.ok(apps);
			}

			
		}
		catch (Exception e) {
			e.printStackTrace();
			return  ResponseEntity.notFound().build();
		}
	}
	
	
	@PostMapping("/del")
	public String delApp(@RequestBody Map<String, String> jsonData){
		try {
			
			Appointment  appoin=appointmentRepository.getById(Long.parseLong(jsonData.get("id")));
			try {
				EmailDetails mail=new EmailDetails(appoin.getEmail(),"Sorry for not being able to accept your request","apologize letter");
				emailService.sendSimpleMail(mail);
			}catch (Exception e) {
				e.printStackTrace();
			}
			appointmentRepository.deleteById(Long.parseLong(jsonData.get("id")));
			return "Yes";
		}
		catch (Exception e) {
			e.printStackTrace();
			return  "No";
			
		}
	}

	
	
}
