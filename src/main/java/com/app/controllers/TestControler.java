package com.app.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("test")
public class TestControler {
	@GetMapping("/hey")
	public String GetHey() {
		return "hey hey";
	}
	
	@GetMapping("/admin")
	public String GetAd() {
		return "hello ad";
	}
	
}
