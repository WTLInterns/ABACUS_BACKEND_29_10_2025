package com.example.Abacus.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    
}
