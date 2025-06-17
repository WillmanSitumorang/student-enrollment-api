package com.example.student_enrollment_api.dto;

import lombok.Data;

@Data
public class EnrollResponse {
    private Long studentId;
    private Long courseId;
    private String message;
}
