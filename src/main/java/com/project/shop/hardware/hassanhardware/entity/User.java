package com.project.shop.hardware.hassanhardware.entity;

import java.util.Collection;
import java.util.List;

/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class User { //implements UserDetails {
	
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;
	private String name;
	private String username;
	private String password;
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
		super();
	}
	
	public User(long userId) {
		super();
		this.userId = userId;
	}

	public User(String name, String username, String password, String phoneNumber, String role) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		setRole(role);
	}
	public User(long userId, String name, String username, String password, String phoneNumber, String role) {
		super();
		this.userId = userId;
		this.name = name;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		setRole(role);
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
		return role.name();
	}
	
	public void setRole(String role) {
		this.role = Role.Admin.name().equals(role) ? Role.Admin : Role.Employee.name().equals(role) ? Role.Employee : Role.CEO;
	}
	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
*/
}
