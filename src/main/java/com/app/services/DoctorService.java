package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repositories.DoctorRepository;

@Service
public class DoctorService {
	private DoctorRepository doctorRepository;
	@Autowired
	public DoctorService(DoctorRepository doctorRepository) {
		super();
		this.doctorRepository = doctorRepository;
	}
	
	
}
