package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsernameAndPassword(String username, String password);
	User findUserByEmail(String email);
	User findByUsername(String username);
	User findUserByUserid(Long id);
}
