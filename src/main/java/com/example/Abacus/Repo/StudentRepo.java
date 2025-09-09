package com.example.Abacus.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    
    Student findByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);
    
    java.util.List<Student> findByTeacher_User_UserId(int teacherUserId);

    boolean existsByTeacher(Teacher teacher);
}
