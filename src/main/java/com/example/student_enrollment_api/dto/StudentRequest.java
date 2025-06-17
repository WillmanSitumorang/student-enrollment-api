package com.example.student_enrollment_api.dto;

import lombok.Data;

@Data
public class StudentRequest {
    private String name;
    private String email;
}