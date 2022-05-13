package com.studentsmartcard.app.service;

import java.util.List;

import com.studentsmartcard.app.models.Student;

public interface StudentService {

	public Student getByCardId(String cardId);
	public Student getByEmailId(String email);
	public Student getByRollNo(String rollNo);
	public List<Student> getAll();
	public Student add(Student student);
	public Student removeById(String studentId);
	public Student update(Student student);
	
}
