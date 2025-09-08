package com.example.Abacus.Controller;


import com.example.Abacus.DTO.requests.StudentRequest;
import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Service.StudentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // CREATE
    @PostMapping("/createStudent/{teacherId}")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request, @PathVariable int teacherId) {
        return ResponseEntity.ok(studentService.createStudent(request,teacherId));
    }

    // READ by ID
    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    // READ all
    @GetMapping("/getAllStudent")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable int id, @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully with id " + id);
    }

    // update Status

    @PutMapping("/{studentId}/updateStatus")
	public ResponseEntity<Student> changeStatus(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
		String status = requestBody.get("status");

		try {
			Student updatedOrder = studentService.udpateStatus(id, status);
			return ResponseEntity.ok(updatedOrder);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

    // update marks
    @PutMapping("/{id}/marks")
    public Student updateMarks(@PathVariable int id, @RequestBody Map<String, Integer> levelMarks) {
        return studentService.updateLevelWiseMark(id, levelMarks);
    }
}
