package com.demo.quinte_backend_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StudentUpdateRequest {

	@Size(max = 100, message = "Name must be at most 100 characters")
	private String name;

	@Email(message = "Email should be valid")
	@Size(max = 255, message = "Email must be at most 255 characters")
	private String email;

	@Pattern(
		regexp = "^$|^[0-9]{10,15}$",
		message = "Phone number length must be between 10 and 15 digits"
	)
	private String phoneNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
