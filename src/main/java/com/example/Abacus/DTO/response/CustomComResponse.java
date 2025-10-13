package com.example.Abacus.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomComResponse {
   
    private String message;
    private int studentId;
    private String studentName;
    private int competitionId;
    private String competitionName;

}
