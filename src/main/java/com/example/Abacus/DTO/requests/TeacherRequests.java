package com.example.Abacus.DTO.requests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequests {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> centers;

    


}
