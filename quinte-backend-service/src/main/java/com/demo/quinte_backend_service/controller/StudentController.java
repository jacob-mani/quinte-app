package com.demo.quinte_backend_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.quinte_backend_service.dto.StudentCreateRequest;
import com.demo.quinte_backend_service.dto.StudentResponse;
import com.demo.quinte_backend_service.dto.StudentUpdateRequest;
import com.demo.quinte_backend_service.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping
	public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest request) {
		StudentResponse created = studentService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping
	public ResponseEntity<List<StudentResponse>> getAll() {
		List<StudentResponse> result = studentService.getAll();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(studentService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudentResponse> update(
		@PathVariable Long id,
		@Valid @RequestBody StudentUpdateRequest request
	) {
		return ResponseEntity.ok(studentService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
		studentService.delete(id);
		return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
	}
}
