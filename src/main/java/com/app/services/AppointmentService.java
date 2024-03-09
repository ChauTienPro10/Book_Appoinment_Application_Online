package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repositories.AppointmentRepository;

@Service
public class AppointmentService {
	private AppointmentRepository appointmentRepository;
	@Autowired
	public AppointmentService(AppointmentRepository appointmentRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
	}
	
}
