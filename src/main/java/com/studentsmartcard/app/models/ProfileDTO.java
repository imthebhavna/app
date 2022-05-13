package com.studentsmartcard.app.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.studentsmartcard.app.models.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {

	@Transient
	private String firstName;
	@Transient
	private String middleName;
	@Transient
	private String lastName;
	
	@Transient
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Transient
	private String email;
	@Transient
	private String mobile;
}
