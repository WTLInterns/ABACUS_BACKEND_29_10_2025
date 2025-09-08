package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {
    
    public Teacher findByFirstNameAndLastName(String firstName, String lastName);
}
