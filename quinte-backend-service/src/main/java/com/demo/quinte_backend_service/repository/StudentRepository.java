package com.demo.quinte_backend_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.quinte_backend_service.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	boolean existsByEmailIgnoreCaseAndDeletedFalse(String email);

	boolean existsByEmailIgnoreCaseAndIdNotAndDeletedFalse(String email, Long id);

	Optional<Student> findByIdAndDeletedFalse(Long id);
}
