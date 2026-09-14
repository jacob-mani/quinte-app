package com.demo.quinte_backend_service.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.quinte_backend_service.dto.StudentCreateRequest;
import com.demo.quinte_backend_service.dto.StudentMapper;
import com.demo.quinte_backend_service.dto.StudentResponse;
import com.demo.quinte_backend_service.dto.StudentUpdateRequest;
import com.demo.quinte_backend_service.entity.Student;
import com.demo.quinte_backend_service.exception.BadRequestException;
import com.demo.quinte_backend_service.exception.DuplicateResourceException;
import com.demo.quinte_backend_service.exception.ResourceNotFoundException;
import com.demo.quinte_backend_service.repository.StudentRepository;
import com.demo.quinte_backend_service.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	@Transactional
	public StudentResponse create(StudentCreateRequest request) {
		String email = normalizeEmail(request.getEmail());
		if (studentRepository.existsByEmailIgnoreCaseAndDeletedFalse(email)) {
			throw new DuplicateResourceException("Email already exists: " + email);
		}

		Student student = new Student();
		student.setName(normalizeRequiredText(request.getName(), "Name"));
		student.setEmail(email);
		student.setPhoneNumber(normalizeRequiredText(request.getPhoneNumber(), "Phone number"));

		Student saved = studentRepository.save(student);
		return StudentMapper.toResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StudentResponse> getAll() {
		//wrap the result in a response object if needed, for now returning the list of StudentResponse
		return studentRepository.findAll()
			.stream()
			.filter(student -> !student.isDeleted())
			.map(StudentMapper::toResponse)
			.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public StudentResponse getById(Long id) {
		Student student = studentRepository
			.findByIdAndDeletedFalse(id)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found for id: " + id));
		return StudentMapper.toResponse(student);
	}

	@Override
	@Transactional
	public StudentResponse update(Long id, StudentUpdateRequest request) {
		Student student = studentRepository
			.findByIdAndDeletedFalse(id)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found for id: " + id));

		boolean changed = false;

		if (request.getName() != null) {
			String normalizedName = normalizeRequiredText(request.getName(), "Name");
			if (!normalizedName.equals(student.getName())) {
				student.setName(normalizedName);
				changed = true;
			}
		}

		if (request.getEmail() != null) {
			String normalizedEmail = normalizeEmail(request.getEmail());
			if (!normalizedEmail.equalsIgnoreCase(student.getEmail())) {
				if (studentRepository.existsByEmailIgnoreCaseAndIdNotAndDeletedFalse(normalizedEmail, id)) {
					throw new DuplicateResourceException("Email already exists: " + normalizedEmail);
				}
				student.setEmail(normalizedEmail);
				changed = true;
			}
		}

		if (request.getPhoneNumber() != null) {
			String normalizedPhone = normalizeRequiredText(request.getPhoneNumber(), "Phone number");
			if (!normalizedPhone.equals(student.getPhoneNumber())) {
				student.setPhoneNumber(normalizedPhone);
				changed = true;
			}
		}

		if (!changed) {
			return StudentMapper.toResponse(student);
		}

		Student saved = studentRepository.save(student);
		return StudentMapper.toResponse(saved);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Student student = studentRepository
			.findByIdAndDeletedFalse(id)
			.orElseThrow(() -> new ResourceNotFoundException("Student not found for id: " + id));
		student.setDeleted(true);
		studentRepository.save(student);
	}

	private String normalizeRequiredText(String value, String fieldName) {
		if (value == null) {
			throw new BadRequestException(fieldName + " cannot be null");
		}
		String normalized = value.trim();
		if (normalized.isEmpty()) {
			throw new BadRequestException(fieldName + " cannot be blank");
		}
		return normalized;
	}


	private String normalizeEmail(String email) {
		String normalized = normalizeRequiredText(email, "Email");
		return normalized.toLowerCase(Locale.ROOT);
	}
}
