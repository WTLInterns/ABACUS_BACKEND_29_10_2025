package com.example.Abacus.Repo;

import com.example.Abacus.Model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District,Integer> {
    List<District> findByStateId(int stateId);
    Optional<District> findByName(String name);
}