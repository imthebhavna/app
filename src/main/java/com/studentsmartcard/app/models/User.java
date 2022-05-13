package com.studentsmartcard.app.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.studentsmartcard.app.models.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class User {

	@Id
	private String id;
	
	// email only at the time of registration
	private String username;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String password;
	
	// to be used in case we want extra basic info 
	// at the time of registration. Example - Faculty
	@Transient
	private ProfileDTO profile;
	
	public User(String username, Role role, String password) {
		this.username = username;
		this.role = role;
		this.password = password;
		this.id = UUID.randomUUID().toString();
	}
}
