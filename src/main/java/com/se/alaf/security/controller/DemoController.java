package com.se.alaf.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	
	@GetMapping("/greet")
	public String printHello() {
		return "Hello from demo";
	} 
	
	@GetMapping("/admin")
	public String admin() {
		return "Hello admin, You are logged in";
	}
	
	@GetMapping("/staff")
	public String hejFromStaff() {
		return "Hello Staff, you are logged in";
	}
}
