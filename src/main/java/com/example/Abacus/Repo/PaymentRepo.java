package com.example.Abacus.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer>{
        Optional<Payment> findByReceiptNo(String receiptNo);

}
