package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Level;


@Repository
public interface LevelRepo extends JpaRepository<Level, Integer> {
    
}
