package com.example.Abacus.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionResponse {
    
    private int id;

 private String competitionName;

    private String heading;

    private String description;

    private String registrationLastDate;

    private String startDate;

    private String endDate;

    private String status;}
