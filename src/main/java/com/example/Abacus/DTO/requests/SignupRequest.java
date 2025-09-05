package com.example.Abacus.DTO.requests;

import com.example.Abacus.Model.User.Role;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private Role role;

    // -----------------for student-------------------
    
}
