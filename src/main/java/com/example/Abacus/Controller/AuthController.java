package com.example.Abacus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.DTO.requests.LoginRequest;
import com.example.Abacus.DTO.requests.SignupRequest;
import com.example.Abacus.DTO.response.LoginResponse;
import com.example.Abacus.DTO.response.SignupResponse;
import com.example.Abacus.Service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;



    // for signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse response = authService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // for login
    @PostMapping("/masterAdminLogin")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.masterAdminLogin(request);
            return ResponseEntity.ok(response); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/teacherLogin")
    public ResponseEntity<?> teacherLogin(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.teacherLogin(request);
            return ResponseEntity.ok(response); 
        } catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }


    


}
