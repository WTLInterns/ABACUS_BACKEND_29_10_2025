package com.example.Abacus.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String competitionName;

    private String heading;

    private String description;

    private String registrationLastDate;

    private String startDate;

    private String endDate;

    private String status;

    // One-to-Many relationship with Student (one competition can have many students)
    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();
    
}