package com.example.Abacus.Repo;


import com.example.Abacus.Model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District,Integer> {
}
