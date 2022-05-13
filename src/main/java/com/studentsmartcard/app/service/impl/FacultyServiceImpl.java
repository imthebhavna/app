package com.studentsmartcard.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.studentsmartcard.app.models.Faculty;
import com.studentsmartcard.app.models.ProfileDTO;
import com.studentsmartcard.app.repository.FacultyRepository;
import com.studentsmartcard.app.service.FacultyService;
import com.studentsmartcard.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FacultyServiceImpl implements FacultyService {

	private UserService userService;
	
	// instead of going for default constructor dependency
	// here we have done lazy setter dependency injection to 
	// avoid circular dependency issue between user and faculty 
	// service.
	// note - faculty service need user service for deletion,
	// user service need faculty service while adding faculty user.
	@Autowired
	public void setUserService(@Lazy UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	@Override
	public Faculty getByEmailId(String email) {
		log.debug("[faculty service]: getting faculty by email id: {}", email);
		return facultyRepository.findByEmail(email);
	}

	@Override
	public List<Faculty> getAll() {
		log.debug("[faculty service]: getting all faculties ...");
		return facultyRepository.findAll();
	}

	@Override
	public Faculty add(ProfileDTO profile) {
		log.debug("[faculty service]: adding new faculty for profile: {}", profile);
		
		Faculty faculty = new Faculty.Builder(profile.getEmail(), profile.getGender())
				.setMobile(profile.getMobile())
				.setFirstName(profile.getFirstName())
				.setMiddleName(profile.getMiddleName())
				.setLastName(profile.getLastName())
				.build();
		
		if(facultyRepository.existsByEmail(faculty.getEmail())) {
			throw new RuntimeException("Faculty already exists for email: " + faculty.getEmail());
		}
		return facultyRepository.save(faculty);
	}

	@Override
	public Faculty removeById(String facultyId) {
		log.debug("[faculty service]: deleting faculty by id: {}", facultyId);
		Faculty deletedFaculty = Optional.ofNullable(
				facultyRepository.findById(facultyId))
				.get()
				.orElseThrow(() -> new RuntimeException("No faculty found for id: " + facultyId))
				;
		
		facultyRepository.deleteById(facultyId);
		userService.removeByUsername(deletedFaculty.getEmail());
		return deletedFaculty;
	}

	@Override
	public Faculty update(Faculty faculty) {
		log.debug("[faculty service]: updating faculty : {}", faculty);
		if(facultyRepository.existsById(faculty.getId())) {
			return facultyRepository.save(faculty);
		}
		throw new RuntimeException("No student found for id: " + faculty.getId());
	}

	@Override
	public Faculty getById(String facultyId) {
		log.debug("[faculty service]: getting faculty by id: {}", facultyId);
		return facultyRepository.findById(facultyId)
				.orElseThrow(() -> new RuntimeException("No faculty found for id: " + facultyId));
	}

}
