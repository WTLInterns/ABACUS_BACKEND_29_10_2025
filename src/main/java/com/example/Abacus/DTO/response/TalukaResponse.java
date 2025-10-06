package com.example.Abacus.DTO.response;

import lombok.Data;

@Data
public class TalukaResponse {
    private int id;
    private String name;
    private int districtId;
    private String districtName;
    
    public TalukaResponse() {}
    
    public TalukaResponse(int id, String name, int districtId, String districtName) {
        this.id = id;
        this.name = name;
        this.districtId = districtId;
        this.districtName = districtName;
    }
}