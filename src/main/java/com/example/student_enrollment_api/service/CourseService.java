package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.dto.CourseRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course addCourse(CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setCredits(request.getCredits());
        return courseRepo.save(course);
    }
}

