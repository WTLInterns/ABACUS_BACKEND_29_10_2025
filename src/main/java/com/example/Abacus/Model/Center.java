package com.example.Abacus.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "taluka_id")
    private Taluka taluka;


}
