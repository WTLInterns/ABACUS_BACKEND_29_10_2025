package com.example.Abacus.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private String teacherId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String markshit;
    private String education;

    private String addharImage;

    private String markshitImage;

    

    private Long fees;
    private String invoice;

    private Long remainingAmount;

    private String role;
        private List<String> branchName;


        private String profilePicture;

        private String ledgerPaidAmount;


    @ManyToOne
    @JoinColumn(name = "master_admin_id")
    private MasterAdmin masterAdmin;



   

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Student> students;
    
    // One-to-Many relationship with Ledger
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ledger> ledgers = new ArrayList<>();

    private String outstandingAmount;


}