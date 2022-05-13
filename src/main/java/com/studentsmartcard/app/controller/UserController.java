package com.studentsmartcard.app.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.studentsmartcard.app.models.Student;
import com.studentsmartcard.app.models.User;
import com.studentsmartcard.app.models.UserDTO;
import com.studentsmartcard.app.service.StudentService;
import com.studentsmartcard.app.service.UserService;
import com.studentsmartcard.app.utils.EmailUtil;
import com.studentsmartcard.app.utils.JwtTokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {	

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService; 
	
	@PostMapping(value="/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		User registeredUser = userService.add(user);
		// removing sensitive information i.e. password from response 
		registeredUser.setPassword(null);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(registeredUser);
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody UserDTO request) {
		
		String username = request.getUsername();
		
		// support for cardId aka RFID 
		if(!EmailUtil.isEmail(username)) {
			try {
				Long temp = Long.parseLong(request.getUsername());
				Student student = studentService.getByCardId(request.getUsername());
				username = student.getEmail();
			} catch(NumberFormatException ex) {
				log.error("Invalid cardId : {}, please enter a valid cardId", request.getUsername());
				throw new RuntimeException("Invalid cardId : " + request.getUsername() + ", please enter a valid cardId.");
			}
		}
		// logic for cardId support ends here
		
		authenticate(username, request.getPassword());
		
		UserDetails userDetails = userService.loadUserByUsername(username);
		
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("username", username);
		response.put("message", "login successful");
		response.put("role", userDetails.getAuthorities().stream().findFirst());
		String token = jwtTokenUtils.generateToken(userDetails);
		
		// preparing response headers
		HttpHeaders headers = new HttpHeaders();
		// adding jwt to headers
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		
		// adding permission header to expose authorization header to client
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(response);
	}
	
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException dx) {
			throw new RuntimeException("USER_DISABLED", dx);
		} catch (BadCredentialsException bx) {
			throw new RuntimeException("INVALID_CREDENTIALS", bx);
		}
	}
}
