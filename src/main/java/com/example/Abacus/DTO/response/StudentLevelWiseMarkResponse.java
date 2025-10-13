package com.example.Abacus.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLevelWiseMarkResponse {
    
    private String studentName;

    private String level;

    private String marks;
}