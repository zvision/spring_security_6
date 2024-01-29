package com.se.alaf.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class SpringSecurity6Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity6Application.class, args);
	}
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
		UserDetails alaf = User.builder().username("alaf").password(passwordEncoder.encode("password"))
				.authorities("staff", "admin").build();
	
		UserDetails staff = User.builder().username("staff").password(passwordEncoder.encode("password"))
				.authorities("staff").build();

		return new InMemoryUserDetailsManager(alaf, staff);
	}
	

}
