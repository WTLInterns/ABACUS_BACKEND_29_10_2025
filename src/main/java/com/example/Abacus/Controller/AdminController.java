package com.example.Abacus.Controller;

import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin/students")
public class AdminController {

    private final StudentService studentService;

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET all students for admin view
    @GetMapping
    public List<StudentResponse> getAllStudentsForAdmin() {
        return studentService.getAllStudents();
    }

    // GET student by ID for admin view (detailed view)
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentByIdForAdmin(@PathVariable int id) {
        try {
            StudentResponse student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // UPDATE student status by admin
    @PutMapping("/{studentId}/updateStatus")
    public ResponseEntity<Student> updateStudentStatus(@PathVariable int studentId, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");

        try {
            Student updatedStudent = studentService.udpateStatus(studentId, status);
            return ResponseEntity.ok(updatedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}