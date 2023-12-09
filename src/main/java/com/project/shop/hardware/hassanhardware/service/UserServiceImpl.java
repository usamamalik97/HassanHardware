package com.project.shop.hardware.hassanhardware.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
/*import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;*/
//import org.springframework.security.core.userdetails.UserDetailsService;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.beans.AuthenticationResponse;
import com.project.shop.hardware.hassanhardware.config.JwtService;
import com.project.shop.hardware.hassanhardware.entity.Role;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@Service
public class UserServiceImpl extends DaoConnectionService implements UserService {

	@Autowired
	private BillService billService;

	@Autowired
	private JwtService jwtService;
	
	@Override
	public User authenticateUser(User entityObject) {
		List<User> users = userRepository.findByUsernameAndPassword(entityObject.getUsername(), entityObject.getPassword());
		if(users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	@Override
	public User authenticate(User entityObject, PasswordEncoder passwordEncoder) {
//	public AuthenticationResponse authenticate(User entityObject) {
		
		User user = userRepository.findByUsername(entityObject.getUsername());
		return passwordEncoder.matches(entityObject.getPassword(), user.getPassword()) ? user : null;
		/*var jwtToken = jwtService.generateToken(user);
		return new AuthenticationResponse(jwtToken);*/
	}

	@Override
	public List<User> getHomeDataUsers() {
		return userRepository.findTop5ByOrderByUserId();
	}

	@Override
	public User saveUser(User entityObject) {
		entityObject.setRole(entityObject.getRole().equals("ADMIN") ? Role.Admin.name() : entityObject.getRole().equals("EMPLOYEE") ? Role.Employee.name() : Role.CEO.name());
		User user = userRepository.save(entityObject);
		return user;
	}
	
	@Override
	//public AuthenticationResponse saveNewUser(User entityObject) {
	public User saveNewUser(User entityObject) {
		entityObject.setRole(entityObject.getRole().equals(Constants.ROLE_ADMIN) ? Role.Admin.name() : entityObject.getRole().equals("EMPLOYEE") ? Role.Employee.name() : Role.CEO.name());
		User user = userRepository.save(entityObject);
		return user;
		/*var jwtToken = jwtService.generateToken(user);
		return new AuthenticationResponse(jwtToken);*/
	}
	@Override
	public boolean deleteUser(User entityObject) {
		Optional<User> user = userRepository.findById(entityObject.getUserId());
		if(user.isPresent()) {
			User userObject = user.get();
			userRepository.delete(userObject);
			return true;
		}

		return false;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User changeRole(User entityObject) {
		Optional<User> user = userRepository.findById(entityObject.getUserId());
		if(user.isPresent()) {
			User userObject = user.get();
			userObject.setRole(userObject.getRole().equals(Constants.ROLE_ADMIN) ? Role.Employee.name() : Role.Admin.name());
			return userRepository.save(userObject);
		}
		return entityObject;
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	/*@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUsername(username);
	}*/
	
/*
	@Bean
	private PasswordEncoder passwordEncoder() {		
		return new BCryptPasswordEncoder();
	}
	@Bean
	private AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {		
		return config.getAuthenticationManager();
	}
	
	@Override
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}*/

}
