package com.studentsmartcard.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studentsmartcard.app.models.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

	public List<Student> findAll();
	public Student findByCardId(String cardId);
	public Student findByEmail(String email);
	public Student findByRollNo(String rollNo);
	
	public boolean existsByEmail(String email);
	public boolean existsByCardId(String cardId);
}
