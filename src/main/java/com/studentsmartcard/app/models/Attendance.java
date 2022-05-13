package com.studentsmartcard.app.models;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.studentsmartcard.app.models.enums.AttendanceStatus;
import com.studentsmartcard.app.models.enums.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Attendance")
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

	@Id
	private String id;
	
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
