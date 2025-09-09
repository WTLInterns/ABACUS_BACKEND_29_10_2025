package com.example.Abacus.Repo;

import com.example.Abacus.Model.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {
    Optional<Foundation> findByName(String name);
}
