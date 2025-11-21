package com.example.Abacus.Controller;

import com.example.Abacus.DTO.requests.StudentRequest;
import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.DTO.response.StudentLevelWiseMarkResponse;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Service.StudentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ArrayList;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // CREATE
    @PostMapping("/createStudent/{teacherUserId}")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request, @PathVariable int teacherUserId) {
        return ResponseEntity.ok(studentService.createStudent(request, teacherUserId));
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
	public ResponseEntity<Student> changeStatus(@PathVariable int studentId, @RequestBody Map<String, String> requestBody) {
		String status = requestBody.get("status");

		try {
			Student updatedStudent = studentService.udpateStatus(studentId , status);
			return ResponseEntity.ok(updatedStudent);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

    @PutMapping("/{studentId}/promoteLevel")
    public ResponseEntity<Student> promoteLevel(@PathVariable int studentId, @RequestBody Map<String, String> requestBody) {
        String level = requestBody.get("level");
        try {
            Student updatedStudent = studentService.promoteLevel(studentId, level);
            return ResponseEntity.ok(updatedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Add this new endpoint to get students by state
    @GetMapping("/state/{stateName}")
    public ResponseEntity<List<StudentResponse>> getStudentsByState(@PathVariable String stateName) {
        return ResponseEntity.ok(studentService.getStudentsByState(stateName));
    }

    // update marks
    @PutMapping("/{id}/marks")
    public Student updateMarks(@PathVariable int id, @RequestBody Map<String, Integer> levelMarks) {
        return studentService.updateLevelWiseMark(id, levelMarks);
    }

    // GET students by teacher user ID
    @GetMapping("/teacher/{teacherUserId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByTeacher(@PathVariable int teacherUserId) {
        return ResponseEntity.ok(studentService.getStudentsByTeacherUserId(teacherUserId));
    }
    
    // GET only approved students by teacher user ID
    @GetMapping("/teacher/{teacherUserId}/approved")
    public ResponseEntity<List<StudentResponse>> getApprovedStudentsByTeacher(@PathVariable int teacherUserId) {
        return ResponseEntity.ok(studentService.getApprovedStudentsByTeacherUserId(teacherUserId));
    }
    
    // GET student level and marks by student ID
    @GetMapping("/{id}/levels-marks")
    public ResponseEntity<List<StudentLevelWiseMarkResponse>> getStudentLevelsAndMarks(@PathVariable int id) {
        try {
            Student student = studentService.getStudentEntityById(id);
            List<StudentLevelWiseMarkResponse> responseList = new ArrayList<>();
            
            if (student != null && student.getLevelWiseMark() != null) {
                // Convert the levelWiseMark map to StudentLevelWiseMarkResponse objects
                for (Map.Entry<String, Integer> entry : student.getLevelWiseMark().entrySet()) {
                    StudentLevelWiseMarkResponse response = new StudentLevelWiseMarkResponse();
                    response.setStudentName(student.getFirstName() + " " + 
                        (student.getMiddleName() != null ? student.getMiddleName() + " " : "") + 
                        student.getLastName());
                    response.setLevel(entry.getKey());
                    response.setMarks(entry.getValue() != null ? entry.getValue().toString() : "0");
                    responseList.add(response);
                }
            }
            
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/teacher/{teacherUserId}/competition-assign")
    public List<StudentResponse> getStudentsByTeacherUserIdAndCompetitionAssign(@PathVariable int teacherUserId) {
        return studentService.getStudentsByTeacherUserIdAndCompetitionAssign(teacherUserId);
    }
}