package com.example.Abacus.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    
    private int id;

private String itemName;

   private int quantity;

   private Long pricePerItem;}
