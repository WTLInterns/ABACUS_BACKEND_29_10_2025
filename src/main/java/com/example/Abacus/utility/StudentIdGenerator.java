package com.example.Abacus.utility;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class StudentIdGenerator {

    @PersistenceContext
    private EntityManager entityManager;

    private static final int INITIAL_ID = 100;

    @Transactional
    public int generateNextStudentId() {
        // Try to get the maximum existing ID
        Integer maxId = (Integer) entityManager
                .createQuery("SELECT MAX(s.id) FROM Student s")
                .getSingleResult();

        // If no students exist or max ID is less than initial ID, start from initial ID
        if (maxId == null || maxId < INITIAL_ID) {
            return INITIAL_ID;
        }

        // Return next ID
        return maxId + 1;
    }
}