package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Repo.TeacherRepo;

@Service
public class TeacherService {
    

    @Autowired
    private TeacherRepo teacherRepository;

    
}
