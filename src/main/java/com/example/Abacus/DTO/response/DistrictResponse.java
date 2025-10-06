package com.example.Abacus.DTO.response;

import lombok.Data;

@Data
public class DistrictResponse {
    private int id;
    private String name;
    private int stateId;
    private String stateName;
    
    public DistrictResponse() {}
    
    public DistrictResponse(int id, String name, int stateId, String stateName) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
        this.stateName = stateName;
    }
}