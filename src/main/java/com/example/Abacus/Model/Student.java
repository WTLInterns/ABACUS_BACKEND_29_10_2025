package com.example.Abacus.Model;

import com.example.Abacus.Model.User.Role;

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

    private String taluka;

    private String address; 

    private String city;

    private String email;

@Enumerated(EnumType.STRING)
private Role role = Role.STUDENT;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    


    
}
