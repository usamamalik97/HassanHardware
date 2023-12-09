package com.project.shop.hardware.hassanhardware.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.authentication.AuthenticationProvider;

import com.project.shop.hardware.hassanhardware.beans.AuthenticationResponse;
import com.project.shop.hardware.hassanhardware.entity.User;

public interface UserService {

	User authenticate(User entityObject, PasswordEncoder passwordEncoder);

	List<User> getHomeDataUsers();

	User saveUser(User entityObject);
	
	boolean deleteUser(User entityObject);
	
	List<User> getUsers();

	User changeRole(User entityObject);

	User getUser(String username);

	//AuthenticationProvider getAuthenticationProvider();

	User saveNewUser(User entityObject);

	User authenticateUser(User entityObject);
	
}
