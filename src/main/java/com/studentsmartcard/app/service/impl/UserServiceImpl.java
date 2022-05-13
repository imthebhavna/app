package com.studentsmartcard.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentsmartcard.app.models.User;
import com.studentsmartcard.app.models.enums.Role;
import com.studentsmartcard.app.repository.UserRepository;
import com.studentsmartcard.app.service.FacultyService;
import com.studentsmartcard.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private FacultyService facultyService;
	
	// instead of going for default constructor dependency
	// here we have done lazy setter dependency injection to 
	// avoid circular dependency issue between user and faculty 
	// service.
	// note - faculty service need user service for deletion,
	// user service need faculty service while adding faculty user.
	@Autowired
	public void setFacultyService(@Lazy FacultyService facultyService) {
		this.facultyService = facultyService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	private List<GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.name()));
		return authorities;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("[user service]: loading user by username: {}", username);
		User user = getByUsername(username);
		
		if(Objects.nonNull(user)) {
			List<GrantedAuthority> authorities = getAuthorities(user.getRole());
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
		}
		throw new RuntimeException("User not found for username: " + username);
	}

	@Override
	public User getByUsername(String username) {
		log.debug("[user service]: getting user by username: {}", username);
		return userRepository.findByUsername(username);
	}

	@Override
	public User getById(String userId) {
		log.debug("[user service]: getting user by id: {}", userId);
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("No user found for id: " + userId))
				;
	}

	@Override
	public User add(User user) {
		log.debug("[user service]: adding new user : {}", user);
		if(userRepository.existsByUsername(user.getUsername())) {
			throw new RuntimeException("User already exists for username: " + user.getUsername());
		}
		if(Objects.isNull(user.getId())) {
			user.setId(UUID.randomUUID().toString());
			user.setPassword(passwordEncoder().encode(user.getPassword()));
		}
		
		if(Role.ROLE_FACULTY.name().equals(user.getRole().name())) {
			if(!Objects.isNull(user.getProfile())) {
				user.getProfile().setEmail(user.getUsername());
				log.debug("[user service]: adding new faculty: {}", user.getUsername());
				facultyService.add(user.getProfile());
			}
			
		}
		return userRepository.save(user);
	}

	@Override
	public User removeByUsername(String username) {
		log.debug("[user service]: removing user by username: {}", username);
		User deletedUser = userRepository.findByUsername(username);
		userRepository.deleteByUsername(username);
		return deletedUser;
	}

}
