package com.studentsmartcard.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentsmartcard.app.models.Attendance;
import com.studentsmartcard.app.models.enums.Subject;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
	
	Boolean existsByCardIdAndDate(String cardId, LocalDate date);
	
	Boolean existsByRollNoAndDate(String rollNo, LocalDate date);

	public List<Attendance> findByRollNoAndFacultyId(String rollNo, String facultyId);
	
	public List<Attendance> findByCardIdAndFacultyId(String cardId, String facultyId);
	
	public List<Attendance> findByRollNoAndDateAndSubject(String rollNo, LocalDate date, Subject subject);
	
	@Query("Select a from Attendance a where a.cardId = :cardId and a.date = :date and a.subject = :subject")
	public List<Attendance> findByCardIdAndDateAndSubject(@Param("cardId") String cardId, @Param("date") LocalDate date, @Param("subject") Subject subject);
}
