package com.studentsmartcard.app.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.studentsmartcard.app.models.enums.Branch;
import com.studentsmartcard.app.models.enums.Course;
import com.studentsmartcard.app.models.enums.Semester;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="Student")
@Table(name="student")
@ToString
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Student extends BaseEntity {
	
	private String rollNo;
	private String cardId;
	
	@Enumerated(EnumType.STRING)
	private Course course;
	
	@Enumerated(EnumType.STRING)
	private Branch branch;
	
	@Enumerated(EnumType.STRING)
	private Semester semester;
	
	private Double amount=0.0;
}
