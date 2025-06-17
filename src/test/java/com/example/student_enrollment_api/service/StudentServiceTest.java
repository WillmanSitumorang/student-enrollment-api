package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.dto.StudentRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.repository.CourseRepository;
import com.example.student_enrollment_api.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepo = mock(StudentRepository.class);
        courseRepo = mock(CourseRepository.class);
        studentService = new StudentService(studentRepo, courseRepo);
    }

    @Test
    void testAddStudent_shouldSaveNewStudent() {
        // Arrange
        StudentRequest request = new StudentRequest();
        request.setName("Budi");
        request.setEmail("budi@example.com");

        when(studentRepo.existsByEmail("budi@example.com")).thenReturn(false);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Budi");
        savedStudent.setEmail("budi@example.com");

        when(studentRepo.save(any(Student.class))).thenReturn(savedStudent);

        // Act
        Student result = studentService.addStudent(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Budi", result.getName());
        verify(studentRepo, times(1)).save(any(Student.class));
    }

    @Test
    void testAddStudent_shouldThrowExceptionIfEmailExists() {
        // Arrange
        StudentRequest request = new StudentRequest();
        request.setName("Ani");
        request.setEmail("ani@example.com");

        when(studentRepo.existsByEmail("ani@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentService.addStudent(request));
        verify(studentRepo, never()).save(any());
    }

    @Test
    void testEnrollStudent_shouldAddCourseToStudent() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 100L;

        Student student = new Student();
        student.setId(studentId);
        student.setName("Cici");
        student.setEmail("cici@example.com");
        student.setCourses(new HashSet<>());

        Course course = new Course();
        course.setId(courseId);
        course.setTitle("Matematika");

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepo.save(any(Student.class))).thenReturn(student);

        // Act
        studentService.enrollStudent(studentId, courseId);

        // Assert
        assertTrue(student.getCourses().contains(course));
        verify(studentRepo).save(student);
    }

    @Test
    void testEnrollStudent_shouldThrowIfAlreadyEnrolled() {
        // Arrange
        Long studentId = 1L;
        Long courseId = 100L;

        Course course = new Course();
        course.setId(courseId);
        course.setTitle("Biologi");

        Student student = new Student();
        student.setId(studentId);
        student.setName("Dedi");
        student.setEmail("dedi@example.com");
        student.setCourses(new HashSet<>(List.of(course)));

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentService.enrollStudent(studentId, courseId));
        verify(studentRepo, never()).save(any());
    }

    @Test
    void testGetCoursesByStudent_shouldReturnCourses() {
        // Arrange
        Long studentId = 1L;

        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Fisika");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Kimia");

        Student student = new Student();
        student.setId(studentId);
        student.setCourses(new HashSet<>(Arrays.asList(course1, course2)));

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));

        // Act
        List<Course> result = studentService.getCoursesByStudent(studentId);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(course1));
        assertTrue(result.contains(course2));
    }

    @Test
    void testGetCoursesByStudent_shouldThrowIfStudentNotFound() {
        // Arrange
        Long studentId = 99L;
        when(studentRepo.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentService.getCoursesByStudent(studentId));
    }
}
