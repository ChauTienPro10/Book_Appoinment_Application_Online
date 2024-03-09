package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repositories.ConfirmRepository;

@Service
public class ConfirmService {
	private ConfirmRepository confirmRepository;
	@Autowired
	public ConfirmService(ConfirmRepository confirmRepository) {
		super();
		this.confirmRepository = confirmRepository;
	}
	
	
}
