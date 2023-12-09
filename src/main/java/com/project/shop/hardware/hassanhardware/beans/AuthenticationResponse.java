package com.project.shop.hardware.hassanhardware.beans;

import lombok.Builder;

@Builder
public class AuthenticationResponse {
	
	private String token;

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}

	public AuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
