package com.studentsmartcard.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentsmartcard.app.models.Student;
import com.studentsmartcard.app.models.enums.Branch;
import com.studentsmartcard.app.models.enums.Course;
import com.studentsmartcard.app.models.enums.Gender;
import com.studentsmartcard.app.models.enums.Role;
import com.studentsmartcard.app.service.StudentService;
import com.studentsmartcard.app.utils.EmailUtil;

@RestController
@RequestMapping("/api/students/")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping(value="")
	public List<Student> getAll() {
		return studentService.getAll();
	}
	
	@GetMapping(value="{param}")
	public Student getByCardIdorEmail(@PathVariable String param) {
		if(!ObjectUtils.isEmpty(param) && EmailUtil.isEmail(param)) {
			return studentService.getByEmailId(param);
		}
		return studentService.getByCardId(param);
	}
	
	@DeleteMapping(value = "{studentId}") 
	public Student removeById(@PathVariable String studentId) {
		return studentService.removeById(studentId);
	}
	
	@PostMapping(value="")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(studentService.add(student));
	}
	
	@PutMapping(value="")
	public Student updateStudent(@RequestBody Student student) {
		return studentService.update(student);
	}
	
	@GetMapping(value="configurations")
	public Map<String, Map<String, String>> getConfigurations() {
		Map<String, Map<String, String>> configurations = new LinkedHashMap<>();
		
		// setting genders 
		Map<String, String> genders = new LinkedHashMap<>();
		for(Gender gender : Gender.values()) {
			genders.put(gender.getValue(), gender.name());
		}
		// setting courses
		Map<String, String> courses = new LinkedHashMap<>();
		for(Course course: Course.values()) {
			courses.put(course.getValue(), course.name());
		}
		// setting branches
		Map<String, String> branches = new LinkedHashMap<>();
		for(Branch branch: Branch.values()) {
			branches.put(branch.getValue(), branch.name());
		}
		// setting roles
		Map<String, String> roles = new LinkedHashMap<>();
		// since only valid role for student is ROLE_STUDENT
		roles.put(Role.ROLE_STUDENT.getValue(), Role.ROLE_STUDENT.name());
		
		configurations.put("genders", genders);
		configurations.put("courses", courses);
		configurations.put("branches", branches);
		configurations.put("roles", roles);
		
		return configurations;
	}
}
