package com.studentsmartcard.app.service;

import java.util.List;

import com.studentsmartcard.app.models.Attendance;
import com.studentsmartcard.app.models.AttendanceDTO;

public interface AttendanceService {

	public Attendance create(AttendanceDTO dto);
	
	public List<Attendance> getByStudent(AttendanceDTO dto);
	
	public Attendance removeById(String attendanceId);
}
