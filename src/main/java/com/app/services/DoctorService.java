package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entites.Doctor;
import com.app.repositories.DoctorRepository;

import antlr.collections.List;

@Service
public class DoctorService {
	private DoctorRepository doctorRepository;
	@Autowired
	public DoctorService(DoctorRepository doctorRepository) {
		super();
		this.doctorRepository = doctorRepository;
	}
	
	public java.util.List<Doctor> getAllDoctor() {
		return doctorRepository.findAll();
	}
	
	public Doctor AddNewDoctor(Doctor dr) {
		return doctorRepository.save(dr);
	}
	
	public boolean DelDoctor(Doctor dr) {
		try {
			doctorRepository.delete(dr);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
}
