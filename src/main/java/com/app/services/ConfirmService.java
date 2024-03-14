package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entites.Appointment;
import com.app.entites.Confirm;
import com.app.entites.Doctor;
import com.app.repositories.AppointmentRepository;
import com.app.repositories.ConfirmRepository;
import com.app.repositories.DoctorRepository;

@Service
public class ConfirmService {
	private ConfirmRepository confirmRepository;
	private DoctorRepository doctorRepository;
	private AppointmentRepository appointmentRepository;
	@Autowired
	public ConfirmService(
			ConfirmRepository confirmRepository
			,DoctorRepository doctorRepository
			,AppointmentRepository appointmentRepository) {
		super();
		this.confirmRepository = confirmRepository;
		this.doctorRepository=doctorRepository;
		this.appointmentRepository =appointmentRepository;
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
			confirmRepository.delete(cf);
			Appointment appointment=new Appointment(
					cf.getUserid(),
					cf.getDoctorid(),
					cf.getTimeof(),
					cf.getMeettime());
			appointmentRepository.save(appointment);
			return appointment;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
