package com.example.Abacus.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String country;


    private String taluka;

    private String address; 

    private String city;

    private String registerNo;

    @ElementCollection
    @CollectionTable(name = "student_level_marks", joinColumns = @JoinColumn(name = "student_id"))
    @MapKeyColumn(name = "level")
    @Column(name = "mark")
    private Map<String, Integer> levelWiseMark = new HashMap<>();
    
    private String email;

    private String status;

    
    private String role;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @OneToMany(mappedBy = "student")
    private List<Competition> competition;

    

    




}
