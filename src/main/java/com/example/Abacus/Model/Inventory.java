package com.example.Abacus.Model;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   private String itemName;

   private int quantity;

   private Long pricePerItem;





}
