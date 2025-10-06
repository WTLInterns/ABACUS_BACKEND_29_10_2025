package com.example.Abacus.FilterUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Student;
import com.example.Abacus.Repo.StudentRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.criteria.Predicate;

@Service
public class FilterService {


    @Autowired
    private StudentRepo studentRepo;

    @PersistenceContext
    private EntityManager entityManager;


    public List<Student> filterStudents(String state, String district, String taluka, String city) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> studentRoot = query.from(Student.class);

        List<Predicate> predicates = new ArrayList<>();

        if (state != null && !state.isEmpty()) {
            predicates.add(cb.equal(studentRoot.get("state"), state));
        }
        if (district != null && !district.isEmpty()) {
            predicates.add(cb.equal(studentRoot.get("district"), district));
        }
        if (taluka != null && !taluka.isEmpty()) {
            predicates.add(cb.equal(studentRoot.get("taluka"), taluka));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(studentRoot.get("city"), city));
        }

        query.select(studentRoot).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Student> getStudentsByStatus(String status) {
        return studentRepo.findByStatus(status);
    }


    public List<Student> getStudentByCenter(String center) {
    return studentRepo.findAll().stream()
            .filter(student -> center.equals(student.getCenter()))
            .collect(Collectors.toList());
}

    
}
