package com.example.Abacus.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Ledger;

@Repository
public interface LedgerRepo extends JpaRepository<Ledger, Integer> {
    
    @Query("SELECT l FROM Ledger l WHERE l.teacher.id = :teacherId")
    List<Ledger> findByTeacherId(@Param("teacherId") int teacherId);
}