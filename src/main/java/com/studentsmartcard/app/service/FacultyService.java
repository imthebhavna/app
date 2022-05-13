package com.studentsmartcard.app.service;

import java.util.List;

import com.studentsmartcard.app.models.Faculty;
import com.studentsmartcard.app.models.ProfileDTO;

public interface FacultyService {

	public Faculty getByEmailId(String email);
	public Faculty getById(String facultyId);
	public List<Faculty> getAll();
	public Faculty add(ProfileDTO profile);
	public Faculty removeById(String facultyId);
	public Faculty update(Faculty faculty);
}
