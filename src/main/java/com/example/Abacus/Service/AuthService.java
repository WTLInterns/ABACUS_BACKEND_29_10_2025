package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.LoginRequest;
import com.example.Abacus.DTO.requests.SignupRequest;
import com.example.Abacus.DTO.response.LoginResponse;
import com.example.Abacus.DTO.response.SignupResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.Abacus.Model.MasterAdmin;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Model.User;
import com.example.Abacus.Repo.MasterAdminRepo;
import com.example.Abacus.Repo.StudentRepo;
import com.example.Abacus.Repo.TeacherRepo;
import com.example.Abacus.Repo.UserRepo;

@Service
public class AuthService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MasterAdminRepo masterAdminRepo;

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

// for sign up for all kind of roles
    public SignupResponse signup(SignupRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole());

        userRepo.save(newUser);

        switch (request.getRole()) {
            case MASTER_ADMIN -> {
                MasterAdmin masterAdmin = new MasterAdmin();
                masterAdmin.setName(request.getName());
                masterAdmin.setEmail(request.getEmail());
                masterAdmin.setPassword(request.getPassword());
                // masterAdmin.setId(request.getId());
                masterAdmin.setUser(newUser);
                // masterAdmin.setPassword();
                masterAdminRepo.save(masterAdmin);
            }
            case TEACHER -> {
                Teacher teacher = new Teacher();
                teacher.setName(request.getName());
                teacher.setEmail(request.getEmail());
                teacher.setPassword(request.getPassword());
                // teacher.setId(request.getId());
                teacher.setUser(newUser);
                teacherRepo.save(teacher);
            }
            case STUDENT -> {
                Student student = new Student();
                student.setName(request.getName());
                student.setEmail(request.getEmail());
                
                // student.setUser(newUser);
                studentRepo.save(student);
            }
            default -> throw new RuntimeException("Invalid role");
        }

        return new SignupResponse("User registered successfully", newUser.getEmail(), newUser.getRole());
    }


//for login all kind of roles
 public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return new LoginResponse("Login successful", user.getEmail(), user.getRole());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

}
