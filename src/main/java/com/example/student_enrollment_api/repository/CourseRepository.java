package com.example.student_enrollment_api.repository;

import com.example.student_enrollment_api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
