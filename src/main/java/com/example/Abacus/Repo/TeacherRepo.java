package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.MasterAdmin;
import com.example.Abacus.Model.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {
    
    Teacher findByFirstNameAndLastName(String firstName, String lastName);
    
    List<Teacher> findByMasterAdmin(MasterAdmin masterAdmin);
    
    Optional<Teacher> findByEmail(String email);
    
    List<Teacher> findByMasterAdminId(int masterAdminId);
    
}