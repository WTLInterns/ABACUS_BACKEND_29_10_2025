package com.example.Abacus.DTO.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String masterAdminName;
    private String role;
    private List<String> branchNames;
    private String profilePicture; // Add profile picture URL
    private String teacherId;
    
    // Add fees related fields
    private Long fees;
    private Long remainingAmount;
    private Long paid;
    
    // Add additional fields
    private String education;
    private String markshit;
    private String invoice;
    private String paymentType;
    private String markshitImage;
    private String aadharImage;
}