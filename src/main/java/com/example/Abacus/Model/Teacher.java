package com.example.Abacus.Model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", unique = true, nullable = false)
    private User user;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    

    @Enumerated(EnumType.STRING)
    private Role role = Role.TEACHER;

    @ManyToOne
    @JoinColumn(name = "master_admin_id")
    private MasterAdmin masterAdmin;

    @OneToMany(mappedBy = "teacher")      
    private List<Student> students;

}
