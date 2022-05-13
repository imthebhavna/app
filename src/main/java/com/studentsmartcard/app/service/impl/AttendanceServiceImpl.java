package com.studentsmartcard.app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.studentsmartcard.app.models.Attendance;
import com.studentsmartcard.app.models.AttendanceDTO;
import com.studentsmartcard.app.models.Student;
import com.studentsmartcard.app.models.enums.Subject;
import com.studentsmartcard.app.repository.AttendanceRepository;
import com.studentsmartcard.app.service.AttendanceService;
import com.studentsmartcard.app.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AttendanceRepository repository;
	
	@Override
	public Attendance create(AttendanceDTO dto) {
		Attendance attendance = null;
		
		// case 1: roll no is empty 
		if(dto.getRollNo() == null || dto.getRollNo().equals("")) {
			// fetch student , to get roll No.
			Student student = studentService.getByCardId(dto.getCardId());
			dto.setRollNo(student.getRollNo());
			// check if attendance already exists, create unique attendance id
			String attendanceId = generateAttendanceId(student.getRollNo(), dto.getSubject(), dto.getDate());
			attendance = repository.findById(attendanceId).orElse(null);
			
			// if attendance already exists
			if(attendance != null) {
				return attendance;
			}
			// else
			attendance = new Attendance();
			BeanUtils.copyProperties(dto, attendance);
			attendance.setId(attendanceId);
		}
		else {
			String attendanceId = generateAttendanceId(dto.getRollNo(), dto.getSubject(), dto.getDate());
			attendance = repository.findById(attendanceId).orElse(null);
			
			// if attendance already exists
			if(attendance != null) {
				return attendance;
			}
			// else
			attendance = new Attendance();
			BeanUtils.copyProperties(dto, attendance);
			attendance.setId(attendanceId);
		}
		
		return repository.save(attendance);
	}
	
	private String generateAttendanceId(String rollNo, Subject subject, LocalDate date) {
		return UUID.nameUUIDFromBytes(rollNo.concat(subject.name())
				.concat(date.toString()).getBytes())
				.toString();
	}
	
	@Override
	public List<Attendance> getByStudent(AttendanceDTO dto) {
		log.debug("[attendance service]: getting attendance for: {}", dto);
		if(!ObjectUtils.isEmpty(dto.getFacultyId())) {
			if(!ObjectUtils.isEmpty(dto.getRollNo())) {
				return repository.findByRollNoAndFacultyId(dto.getRollNo(), dto.getFacultyId());
			}
			
			if(!ObjectUtils.isEmpty(dto.getCardId())) {
				return repository.findByCardIdAndFacultyId(dto.getCardId(), dto.getFacultyId());
			}
			
			throw new RuntimeException("Invalid get attendance request, card id or rollNo is required!");
		} 
		
		throw new RuntimeException("Invalid get attendance request, faculty id is required!");
	}

	@Override
	public Attendance removeById(String attendanceId) {
		log.debug("[attendance service]: deleting attendance by id: {}", attendanceId);
		Attendance deletedAttendance = Optional.ofNullable(
				repository.findById(attendanceId))
				.get()
				.orElseThrow(() -> new RuntimeException("No attendance found for id: " + attendanceId))
				;
		
		repository.deleteById(attendanceId);
		return deletedAttendance;
	}

}
