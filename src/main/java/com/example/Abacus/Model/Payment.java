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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensures auto-increment
    private int id;

    private String receiptNo;
    private String paymentMode;
    private Long paid;
    private Long remainingAmount;
    private Long fees;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
