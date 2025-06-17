package com.example.student_enrollment_api.controller;

import com.example.student_enrollment_api.dto.CourseRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.service.CourseService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Test
    void testAddCourse_shouldReturnCreatedCourse() {
        // Arrange
        CourseService mockService = mock(CourseService.class);
        CourseController controller = new CourseController(mockService);

        CourseRequest request = new CourseRequest();
        request.setTitle("Matematika");
        request.setCredits(3);

        Course saved = new Course();
        saved.setId(1L);
        saved.setTitle("Matematika");
        saved.setCredits(3);

        when(mockService.addCourse(request)).thenReturn(saved);

        // Act
        Course response = controller.addCourse(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Matematika", response.getTitle());
        assertEquals(3, response.getCredits());

        verify(mockService, times(1)).addCourse(request);
    }
}
