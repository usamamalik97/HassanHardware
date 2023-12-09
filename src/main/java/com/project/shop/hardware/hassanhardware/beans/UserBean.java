package com.project.shop.hardware.hassanhardware.beans;

import com.project.shop.hardware.hassanhardware.entity.User;

public class UserBean {
	private long userId;
	private String name;
	private String username;
	private String password;
	private String phoneNumber;
	private String role;

	public UserBean() {
		super();
	}
	
	public UserBean(long userId) {
		super();
		this.userId = userId;
	}

	public UserBean(String name, String username, String password, String phoneNumber, String role) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}
	public UserBean(long userId, String name, String username, String password, String phoneNumber, String role) {
		super();
		this.userId = userId;
		this.name = name;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public User getEntityObject() {
		return new User(userId, name, username, password, phoneNumber, role);
	}
}
