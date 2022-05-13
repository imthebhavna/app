package com.studentsmartcard.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.studentsmartcard.app.models.Student;
import com.studentsmartcard.app.models.User;
import com.studentsmartcard.app.repository.StudentRepository;
import com.studentsmartcard.app.service.StudentService;
import com.studentsmartcard.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Student getByCardId(String cardId) {
		log.debug("[student service]: getting student by card id: {}", cardId);
		return studentRepository.findByCardId(cardId);
	}

	@Override
	public List<Student> getAll() {
		log.debug("[student service]: getting all students ...");
		return studentRepository.findAll();
	}

	@Override
	public Student add(Student student) {
		log.debug("[student service]: adding new student: {}", student);
		
		if(studentRepository.existsByEmail(student.getEmail())) {
			throw new RuntimeException("Student already exists for email: " + student.getEmail());
		}
		
		if(ObjectUtils.isEmpty(student.getId())) {
			student.setId(UUID.randomUUID().toString());
//			String password = passwordEncoder.encode(student.getFirstName() + "@" + student.getRollNo());
			String password = passwordEncoder.encode("test1234");
			User user = new User(student.getEmail(), student.getRole(), password);
			log.debug("[student service]: adding new user: {}", user);
			userService.add(user);
		}
		return studentRepository.save(student);
	}

	@Override
	public Student removeById(String studentId) {
		log.debug("[student service]: deleting student by id: {}", studentId);
		Student deletedStudent = Optional.ofNullable(
				studentRepository.findById(studentId))
				.get()
				.orElseThrow(() -> new RuntimeException("No student found for id: " + studentId))
				;
		
		studentRepository.deleteById(studentId);
		userService.removeByUsername(deletedStudent.getEmail());
		return deletedStudent;
	}

	@Override
	public Student update(Student student) {
		log.debug("[student service]: updating student : {}", student);
		if(studentRepository.existsById(student.getId())) {
			return studentRepository.save(student);
		}
		throw new RuntimeException("No student found for id: " + student.getId());
	}

	@Override
	public Student getByEmailId(String email) {
		log.debug("[student service]: getting student by email id: {}", email);
		return studentRepository.findByEmail(email);
	}

	@Override
	public Student getByRollNo(String rollNo) {
		log.debug("[student service]: getting student by roll No: {}", rollNo);
		return studentRepository.findByRollNo(rollNo);
	}
	
}
