package com.studentsmartcard.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentsmartcard.app.models.Attendance;
import com.studentsmartcard.app.models.AttendanceDTO;
import com.studentsmartcard.app.models.enums.AttendanceStatus;
import com.studentsmartcard.app.models.enums.Gender;
import com.studentsmartcard.app.models.enums.Subject;
import com.studentsmartcard.app.service.AttendanceService;

@RestController
@RequestMapping("/api/attendances/")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@PostMapping(value="")
	public ResponseEntity<Attendance> create(@RequestBody AttendanceDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(attendanceService.create(dto));
	}

	@PostMapping(value="search")
	public ResponseEntity<List<Attendance>> searchByStudent(@RequestBody AttendanceDTO dto) {
		return ResponseEntity.ok(attendanceService.getByStudent(dto));
	}
	
	@GetMapping(value="configurations")
	public Map<String, Map<String, String>> getConfigurations() {
		Map<String, Map<String, String>> configurations = new LinkedHashMap<>(); 
		
		Map<String, String> attendanceStatus = new LinkedHashMap<>();
		for(AttendanceStatus status : AttendanceStatus.values()) {
			attendanceStatus.put(status.getValue(), status.name());
		}
		
		Map<String, String> subjects = new LinkedHashMap<>();
		for(Subject subject : Subject.values()) {
			subjects.put(subject.getValue(), subject.name());
		}
		
		configurations.put("attendanceStatus", attendanceStatus);
		configurations.put("subjects", subjects);
		
		return configurations;
	}
}
