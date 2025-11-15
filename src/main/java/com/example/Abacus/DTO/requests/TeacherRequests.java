package com.example.Abacus.DTO.requests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequests {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String teacherId;

    private String paymentType;

    private List<String> branchName;

    private Long fees;

    private Long paid;

    private Long remainingAmount;
    
    // Added fields from frontend form
    private String education;
    private String markshit;
    private String invoice;
    
    // Profile picture URL
    private String profilePicture;
}