package com.app.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entites.User;


public interface UserRepository extends JpaRepository<User, Long> {
	

	User findUserByUsernameAndPassword(String username, String password);

	User findUserByEmail(String email);

	User findByUsername(String username);

	User findUserByUserid(Long id);
}
