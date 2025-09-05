package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.User;
import com.example.Abacus.Repo.UserRepo;

@Service
public class UserService {
    

    @Autowired
    private UserRepo userRepo;

    
}
