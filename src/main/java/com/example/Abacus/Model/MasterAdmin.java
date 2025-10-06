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
public class MasterAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    @OneToMany(mappedBy = "masterAdmin", cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "masterAdmin", cascade = CascadeType.ALL)
    private List<Inventory> inventory;
}