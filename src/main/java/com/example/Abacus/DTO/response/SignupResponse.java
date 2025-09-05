package com.example.Abacus.DTO.response;

import com.example.Abacus.Model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    private String message;
    private String email;
    private Role role;
}