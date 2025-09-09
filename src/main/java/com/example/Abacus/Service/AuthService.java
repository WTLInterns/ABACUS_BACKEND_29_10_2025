package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.LoginRequest;
import com.example.Abacus.DTO.requests.SignupRequest;
import com.example.Abacus.DTO.response.LoginResponse;
import com.example.Abacus.DTO.response.SignupResponse;

import com.example.Abacus.Model.MasterAdmin;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.MasterAdminRepo;
import com.example.Abacus.Repo.StudentRepo;
import com.example.Abacus.Repo.TeacherRepo;

@Service
public class AuthService {
    
    

    @Autowired
    private MasterAdminRepo masterAdminRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

   

// for sign up for all kind of roles
    public SignupResponse signup(SignupRequest request) {
        if (masterAdminRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

            MasterAdmin masterAdmin = new MasterAdmin();
                masterAdmin.setFirstName(request.getFirstName());
                masterAdmin.setLastName(request.getLastName());
                masterAdmin.setEmail(request.getEmail());

                masterAdmin.setRole("MASTER_ADMIN");
                masterAdmin.setPassword(request.getPassword());
                masterAdminRepo.save(masterAdmin);
            
           

        return new SignupResponse("User registered successfully", masterAdmin.getEmail(), masterAdmin.getRole());
    }


public LoginResponse masterAdminLogin(LoginRequest request) {
        MasterAdmin masterAdmin  = masterAdminRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!masterAdmin.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponse("Login successful", masterAdmin.getEmail(), masterAdmin.getRole(), masterAdmin.getId(), masterAdmin.getFirstName(), masterAdmin.getLastName());
    }



    public LoginResponse teacherLogin(LoginRequest request) {
        Teacher teacher  = teacherRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        if (!teacher.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponse("Login successful", teacher.getEmail(), teacher.getRole(), teacher.getId(), teacher.getFirstName(), teacher.getLastName());
    }

}


          