package com.studentsmartcard.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studentsmartcard.app.models.Faculty;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, String> {

	public List<Faculty> findAll();
	
	public Faculty findByEmail(String email);
	public boolean existsByEmail(String email);
}
