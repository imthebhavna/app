package com.studentsmartcard.app.models;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.studentsmartcard.app.models.enums.AttendanceStatus;
import com.studentsmartcard.app.models.enums.Subject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AttendanceDTO {
	
	private String facultyId;
	private String rollNo;
	private String cardId;
	
	private LocalDate date;
	
	@Enumerated(EnumType.STRING)
	private Month month;
	
	private Year year;
	
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;
	
	@Enumerated(EnumType.STRING)
	private Subject subject;
}
