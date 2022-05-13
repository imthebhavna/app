package com.studentsmartcard.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentsmartcard.app.models.Faculty;
import com.studentsmartcard.app.service.FacultyService;
import com.studentsmartcard.app.utils.EmailUtil;

@RestController
@RequestMapping("/api/faculties/")
public class FacultyController {

	@Autowired
	private FacultyService facultyService;
	
	@GetMapping(value="")
	public List<Faculty> getAll() {
		return facultyService.getAll();
	}
	
	@GetMapping(value="{param}")
	public Faculty getFacultyIdorEmail(@PathVariable String param) {
		if(!ObjectUtils.isEmpty(param) && EmailUtil.isEmail(param)) {
			return facultyService.getByEmailId(param);
		}
		return facultyService.getById(param);
	}
	
	@DeleteMapping(value="{facultyId}")
	public Faculty removeById(@PathVariable String facultyId) {
		return facultyService.removeById(facultyId);
	}
	
	@PutMapping(value="")
	public Faculty updateFaculty(@RequestBody Faculty faculty) {
		return facultyService.update(faculty);
	}
	
	
}
