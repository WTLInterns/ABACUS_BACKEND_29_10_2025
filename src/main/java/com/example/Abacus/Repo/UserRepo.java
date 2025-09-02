package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    
}
