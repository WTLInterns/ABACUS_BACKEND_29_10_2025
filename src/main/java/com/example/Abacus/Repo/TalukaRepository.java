package com.example.Abacus.Repo;

import com.example.Abacus.Model.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalukaRepository extends JpaRepository<Taluka,Integer> {
    List<Taluka> findByDistrictId(int districtId);
}