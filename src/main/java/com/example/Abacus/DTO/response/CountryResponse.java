package com.example.Abacus.DTO.response;

import lombok.Data;
import java.util.List;

@Data
public class CountryResponse {
    private int id;
    private String name;
    
    public CountryResponse() {}
    
    public CountryResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}