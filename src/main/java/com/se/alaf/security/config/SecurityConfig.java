package com.se.alaf.security.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig {

	
	private final UserDetailsService userService;
	
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(req->req
                        .requestMatchers("/greet/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("admin")
                        .requestMatchers("/staff/**").hasAnyAuthority("admin", "staff")
                        .anyRequest().authenticated())
                .userDetailsService(userService);

		http.logout(logout -> logout.logoutSuccessUrl("/").logoutSuccessHandler(new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				String username = userDetails.getUsername();

				log.info("The user {} has logged out!", username );

				response.sendRedirect(request.getContextPath());
			}
		}).invalidateHttpSession(true).clearAuthentication(true));
	
		return http.build();
	}
	

	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();   // used for prod.
		
		// return NoOpPasswordEncoder.getInstance();  //for dev   
	} 
}
