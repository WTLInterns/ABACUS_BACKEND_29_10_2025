package com.example.Abacus.DTO.response;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String email;
    private String role;
    private int id;
    private String firstName;
    private String lastName;
}

