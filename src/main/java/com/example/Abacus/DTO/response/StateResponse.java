package com.example.Abacus.DTO.response;

import lombok.Data;

@Data
public class StateResponse {
    private int id;
    private String name;
    private int countryId;
    private String countryName;
    
    public StateResponse() {}
    
    public StateResponse(int id, String name, int countryId, String countryName) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.countryName = countryName;
    }
}