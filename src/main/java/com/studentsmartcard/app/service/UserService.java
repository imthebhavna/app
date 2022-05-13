package com.studentsmartcard.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.studentsmartcard.app.models.User;

public interface UserService extends UserDetailsService {

	public User getByUsername(String username);
	public User getById(String userId);
	
	public User add(User user);
	public User removeByUsername(String username);
	
}
