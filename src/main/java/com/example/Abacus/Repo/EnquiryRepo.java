package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Enquiry;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, Long> {
    
}
