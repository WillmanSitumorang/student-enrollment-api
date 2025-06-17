package com.example.student_enrollment_api.controller;

import com.example.student_enrollment_api.dto.StudentRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.service.StudentService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Test
    void testAddStudent_shouldReturnCreatedStudent() {
        // Arrange
        StudentService mockService = mock(StudentService.class);
        StudentController controller = new StudentController(mockService);

        StudentRequest request = new StudentRequest();
        request.setName("Andi");
        request.setEmail("andi@mail.com");

        Student saved = new Student();
        saved.setId(1L);
        saved.setName("Andi");
        saved.setEmail("andi@mail.com");

        when(mockService.addStudent(request)).thenReturn(saved);

        // Act
        Student response = controller.addStudent(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Andi", response.getName());
        assertEquals("andi@mail.com", response.getEmail());

        verify(mockService, times(1)).addStudent(request);
    }

    @Test
    void testEnrollStudent_shouldCallService() {
        // Arrange
        StudentService mockService = mock(StudentService.class);
        StudentController controller = new StudentController(mockService);

        Long studentId = 1L;
        Long courseId = 2L;

        // Act
        controller.enrollStudent(studentId, courseId);

        // Assert
        verify(mockService, times(1)).enrollStudent(studentId, courseId);
    }

    @Test
    void testGetCourses_shouldReturnListOfCourses() {
        // Arrange
        StudentService mockService = mock(StudentService.class);
        StudentController controller = new StudentController(mockService);

        Long studentId = 1L;
        Course course1 = new Course();
        course1.setId(10L);
        course1.setTitle("Fisika");
        course1.setCredits(3);

        Course course2 = new Course();
        course2.setId(11L);
        course2.setTitle("Kimia");
        course2.setCredits(2);

        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(mockService.getCoursesByStudent(studentId)).thenReturn(expectedCourses);

        // Act
        List<Course> result = controller.getCourses(studentId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Fisika", result.get(0).getTitle());

        verify(mockService, times(1)).getCoursesByStudent(studentId);
    }
}
