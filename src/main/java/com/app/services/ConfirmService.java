package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entites.Appointment;
import com.app.entites.Confirm;
import com.app.entites.Doctor;
import com.app.entites.User;
import com.app.repositories.AppointmentRepository;
import com.app.repositories.ConfirmRepository;
import com.app.repositories.DoctorRepository;
import com.app.repositories.UserRepository;

@Service
public class ConfirmService {
	
	private ConfirmRepository confirmRepository;
	private DoctorRepository doctorRepository;
	private AppointmentRepository appointmentRepository;
	private UserRepository userRepository;
	@Autowired
	public ConfirmService(
			ConfirmRepository confirmRepository
			,DoctorRepository doctorRepository
			,AppointmentRepository appointmentRepository,UserRepository userRepository) {
		super();
		this.confirmRepository = confirmRepository;
		this.doctorRepository=doctorRepository;
		this.appointmentRepository =appointmentRepository;
		this.userRepository=userRepository;
	}
	public Confirm AddConfirm(Confirm cfrm) {
		try {
			return confirmRepository.save(cfrm);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	public boolean findDoctorById(long idDr) {
		Doctor dr=doctorRepository.getById(idDr);
		if(dr!=null) {
			return true;
		}
		return false;
	}
	public java.util.List<Confirm> getAllConfirm(long iddr) {
		return confirmRepository.findConfirmByDoctorid(iddr);
	}
	
	public Appointment ConfirmAppoinment(long id) {
		try {
			Confirm cf=confirmRepository.findConfirmById(id);
			System.out.println();
			System.out.println(cf.getDoctorid());
			System.out.println();
			Doctor dr=doctorRepository.findDoctorByDoctorid(cf.getDoctorid());
			User us=userRepository.findUserByUserid(dr.getUserid());
			
			Appointment appointment=new Appointment(
					cf.getUserid(),
					cf.getDoctorid(),
					cf.getTimeof(),
					cf.getMeettime(),
					cf.getNameother(),
					us.getName(),
					cf.getMailcustomer()
					);
			appointmentRepository.save(appointment);
			confirmRepository.delete(cf);
			return appointment;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
