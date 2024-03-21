package com.app.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entites.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	Appointment findAppointmentByTimeof(Date dateMeet);
	List<Appointment> getAppointmentByUserid(Long id);
	List<Appointment> getAppointmentByDoctorid(Long id);
}
