package com.example.Abacus.Repo;


import com.example.Abacus.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
    Optional<Country> findByName(String name);
}