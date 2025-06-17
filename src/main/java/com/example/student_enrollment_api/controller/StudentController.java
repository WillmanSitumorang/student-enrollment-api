package com.example.student_enrollment_api.controller;

import com.example.student_enrollment_api.dto.EnrollResponse;
import com.example.student_enrollment_api.dto.StudentRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody StudentRequest request) {
        return studentService.addStudent(request);
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<EnrollResponse> enrollStudent(@PathVariable Long id, @RequestParam Long courseId) {
        studentService.enrollStudent(id, courseId);

        EnrollResponse response = new EnrollResponse();
        response.setStudentId(id);
        response.setCourseId(courseId);
        response.setMessage("Enrollment successful");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/courses")
    public List<Course> getCourses(@PathVariable Long id) {
        return studentService.getCoursesByStudent(id);
    }
}
