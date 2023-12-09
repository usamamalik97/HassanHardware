package com.project.shop.hardware.hassanhardware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shop.hardware.hassanhardware.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findTop5ByOrderByUserId();
	User findByUsername(String username);
}
