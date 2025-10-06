package com.example.Abacus.Repo;

import com.example.Abacus.Model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State,Integer> {
    List<State> findByCountryId(int countryId);
    Optional<State> findByName(String name);
}