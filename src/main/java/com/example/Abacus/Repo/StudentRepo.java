package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    
    Student findByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);
    

    boolean existsByTeacher(Teacher teacher);
}
