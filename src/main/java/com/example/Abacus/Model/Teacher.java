package com.example.Abacus.Model;

import java.util.ArrayList;
import java.util.List;

import com.example.Abacus.Model.User.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String markshit;

    private String education;

    private Long fees;

    private String invoice;

    // private Long paid;

    // private String paymentType;
    
    private Long remainingAmount;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Payment> payments = new ArrayList<>();


    

    

    @Enumerated(EnumType.STRING)
    private Role role = Role.TEACHER;

    @ManyToOne
    @JoinColumn(name = "master_admin_id")
    private MasterAdmin masterAdmin;

    @ManyToOne
    @JoinColumn(name = "teacher")
    private Teacher center;


    @OneToMany(mappedBy = "teacher")      
    private List<Student> students;

}
