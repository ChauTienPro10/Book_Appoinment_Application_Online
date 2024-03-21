package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entites.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	Doctor findDoctorByUserid(long iduser);
	Doctor findDoctorByDoctorid(long doctorid);
}
