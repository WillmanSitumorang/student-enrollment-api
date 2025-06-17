package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.dto.CourseRequest;
import com.example.student_enrollment_api.model.Course;
import com.example.student_enrollment_api.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private final CourseRepository courseRepo = mock(CourseRepository.class);
    private final CourseService courseService = new CourseService(courseRepo);

    @Test
    void testAddCourse_shouldSaveCourseCorrectly() {
        // Arrange
        CourseRequest request = new CourseRequest();
        request.setTitle("Pemrograman Java");
        request.setCredits(3);

        Course saved = new Course();
        saved.setId(1L);
        saved.setTitle("Pemrograman Java");
        saved.setCredits(3);

        when(courseRepo.save(any(Course.class))).thenReturn(saved);

        // Act
        Course result = courseService.addCourse(request);

        // Assert
        assertNotNull(result);
        assertEquals("Pemrograman Java", result.getTitle());
        assertEquals(3, result.getCredits());

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepo, times(1)).save(captor.capture());

        Course captured = captor.getValue();
        assertEquals("Pemrograman Java", captured.getTitle());
        assertEquals(3, captured.getCredits());
    }
}
