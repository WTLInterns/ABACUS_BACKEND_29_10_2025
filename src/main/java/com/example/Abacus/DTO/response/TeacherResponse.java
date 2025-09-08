package com.example.Abacus.DTO.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String masterAdminName;

}
