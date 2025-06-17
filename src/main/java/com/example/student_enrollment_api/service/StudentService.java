package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.dto.StudentRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.repository.CourseRepository;
import com.example.student_enrollment_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    public StudentService(StudentRepository studentRepo, CourseRepository courseRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public Student addStudent(StudentRequest request) {
        if (studentRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        return studentRepo.save(student);
    }

    public void enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (student.getCourses().contains(course)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        student.getCourses().add(course);
        studentRepo.save(student);
    }

    public List<Course> getCoursesByStudent(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return List.copyOf(student.getCourses());
    }
}
