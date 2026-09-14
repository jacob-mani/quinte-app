package com.demo.quinte_backend_service.dto;

import com.demo.quinte_backend_service.entity.Student;

public final class StudentMapper {

	private StudentMapper() {
	}

	public static StudentResponse toResponse(Student student) {
		StudentResponse response = new StudentResponse();
		response.setId(student.getId());
		response.setName(student.getName());
		response.setEmail(student.getEmail());
		response.setPhoneNumber(student.getPhoneNumber());
		response.setCreatedDate(student.getCreatedDate());
		return response;
	}
}
