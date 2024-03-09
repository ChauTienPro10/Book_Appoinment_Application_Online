package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entites.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
