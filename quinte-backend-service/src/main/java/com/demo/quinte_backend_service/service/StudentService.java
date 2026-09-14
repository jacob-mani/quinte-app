package com.demo.quinte_backend_service.service;

import org.springframework.data.domain.Page;

import com.demo.quinte_backend_service.dto.StudentCreateRequest;
import com.demo.quinte_backend_service.dto.StudentResponse;
import com.demo.quinte_backend_service.dto.StudentUpdateRequest;

import java.util.List;

public interface StudentService {

	StudentResponse create(StudentCreateRequest request);

	List<StudentResponse> getAll();

	StudentResponse getById(Long id);

	StudentResponse update(Long id, StudentUpdateRequest request);

	void delete(Long id);
}
