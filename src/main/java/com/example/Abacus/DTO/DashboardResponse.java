package com.example.Abacus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    
    private String teacherName;

    private String email;

    private String enrollmentType;

    private String count;
}
