package com.example.student_enrollment_api.repository;

import com.example.student_enrollment_api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Optional<Student> findById(Long id);
}
