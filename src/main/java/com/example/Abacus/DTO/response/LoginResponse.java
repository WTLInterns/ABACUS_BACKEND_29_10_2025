package com.example.Abacus.DTO.response;


import com.example.Abacus.Model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String email;
    private Role role;
}

