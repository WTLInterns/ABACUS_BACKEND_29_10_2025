package com.example.Abacus.Repo;


import com.example.Abacus.Model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center,Integer> {
}
