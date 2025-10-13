package com.example.Abacus.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private int id;

    // @OneToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "userId")
    // private User user;

    private String enrollMeantType;

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    private String whatsappNumber;

    private String dob;

    private String addmissionDate;

    private String std;

    private String currentLevel;

    private String center;

    private String state;
    
    private String district;

    private String address; 

    private String city;

    private String email;

    private int teacherId;

    private String teacherFirstName;

    private String teacherLastName;

    private String teacherEmail;

    private String country;

    private String taluka;
    
    private String status;
    
    private List<String> branchNames;
    
    private Map<String, Integer> levelWiseMark;

    private List<CompetitionResponse> competitions;
}