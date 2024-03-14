package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entites.Confirm;

public interface ConfirmRepository extends JpaRepository<Confirm, Long>{
	List<Confirm>  findConfirmByDoctorid(Long idDoc);
	Confirm findConfirmById(long id);
}
