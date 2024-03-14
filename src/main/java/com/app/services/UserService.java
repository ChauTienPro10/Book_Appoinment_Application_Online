package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entites.CustomUserDetails;
import com.app.entites.User;
import com.app.repositories.UserRepository;

import javassist.NotFoundException;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	
	

	public User findUserByUsernameAndPassword(String username, String password) {
		return userRepository.findUserByUsernameAndPassword(username, password);
	}

	public User addNewUser(User newUS) {
	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    newUS.setPassword(passwordEncoder.encode(newUS.getPassword()));
	    return userRepository.save(newUS);
	}

	public boolean deleteUser(long id) {
		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public User updateUser(User updatedUser) throws NotFoundException {
		User existingUser = userRepository.findById(updatedUser.getUserid())
				.orElseThrow(() -> new NotFoundException("User not found"));

		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		return userRepository.save(existingUser);
	}

	public User findUserByMail(String mail) {
		return userRepository.findUserByEmail(mail);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new CustomUserDetails(user);
	}

	
	

}
