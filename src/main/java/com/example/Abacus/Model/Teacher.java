package com.example.Abacus.Model;

import java.util.List;

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
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private String name;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "master_admin_id")
    private MasterAdmin masterAdmin;


    @OneToMany(mappedBy = "teacher")
    private List<Student> students;


}
