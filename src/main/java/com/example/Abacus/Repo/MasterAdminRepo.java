package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Abacus.Model.MasterAdmin;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterAdminRepo extends JpaRepository<MasterAdmin, Integer>{
    
}
