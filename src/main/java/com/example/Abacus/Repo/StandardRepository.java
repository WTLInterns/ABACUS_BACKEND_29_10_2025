package com.example.Abacus.Repo;


import com.example.Abacus.Model.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Long> {
    Optional<Standard> findByName(String name);
}
