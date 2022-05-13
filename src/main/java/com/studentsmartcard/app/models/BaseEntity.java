package com.studentsmartcard.app.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.studentsmartcard.app.models.enums.Gender;
import com.studentsmartcard.app.models.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@MappedSuperclass
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

	@Id
	private String id;
	
	private String firstName;
	private String middleName;
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String email;
	private String mobile;
}
