package com.example.student_enrollment_api.controller;

import com.example.student_enrollment_api.dto.CourseRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.service.CourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public Course addCourse(@RequestBody CourseRequest request) {
        return courseService.addCourse(request);
    }
}
