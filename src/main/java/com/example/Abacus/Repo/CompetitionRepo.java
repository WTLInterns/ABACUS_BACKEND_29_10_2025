package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Competition;

@Repository
public interface CompetitionRepo extends JpaRepository<Competition, Integer>{

    
    
}
