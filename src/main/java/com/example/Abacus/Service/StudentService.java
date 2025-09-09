package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.StudentRequest;
import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.StudentRepo;
import com.example.Abacus.Repo.TeacherRepo;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    // CREATE
    public StudentResponse createStudent(StudentRequest request, int teacherId) {
        Student existingByName = studentRepo.findByFirstNameAndMiddleNameAndLastName(
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName()
        );
        if (existingByName != null) {
            throw new IllegalArgumentException("Student already registered");
        }

        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(
                () -> new IllegalArgumentException("Teacher not found with id: " + teacherId)
        );

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());
        student.setGender(request.getGender());
        student.setWhatsappNumber(request.getWhatsappNumber());
        student.setAddress(request.getAddress());
        student.setDob(request.getDob());
        student.setAddmissionDate(request.getAddmissionDate());
        student.setStd(request.getStd());
        student.setCurrentLevel(request.getCurrentLevel());
        student.setCenter(request.getCenter());
        student.setState(request.getState());
        student.setDistrict(request.getDistrict());
        student.setCity(request.getCity());
        student.setEmail(request.getEmail());
        student.setTaluka(request.getTaluka());
        student.setEnrollMeantType(request.getEnrollMeantType());
        student.setTeacher(teacher);

        Student saved = studentRepo.save(student);
        return mapToResponse(saved);
    }

    // READ by ID
    public StudentResponse getStudentById(int id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
        return mapToResponse(student);
    }

    // READ all
    public List<StudentResponse> getAllStudents() {
        return studentRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // UPDATE
    public StudentResponse updateStudent(int id, StudentRequest request) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));

        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());
        student.setGender(request.getGender());
        student.setWhatsappNumber(request.getWhatsappNumber());
        student.setAddress(request.getAddress());
        student.setDob(request.getDob());
        student.setAddmissionDate(request.getAddmissionDate());
        student.setStd(request.getStd());
        student.setCurrentLevel(request.getCurrentLevel());
        student.setCenter(request.getCenter());
        student.setState(request.getState());
        student.setDistrict(request.getDistrict());
        student.setCity(request.getCity());
        student.setEmail(request.getEmail());
        student.setTaluka(request.getTaluka());

        return mapToResponse(studentRepo.save(student));
    }

    // DELETE
    public String deleteStudent(int id) {
        if (!studentRepo.existsById(id)) {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
        studentRepo.deleteById(id);
        return "Student deleted successfully with id: " + id;
    }

    // Mapper
    private StudentResponse mapToResponse(Student student) {
    int teacherId = 0;
    String teacherFirstName = null;
    String teacherLastName = null;
    String teacherEmail = null;

    if (student.getTeacher() != null) {
        teacherId = student.getTeacher().getId();
        teacherFirstName = student.getTeacher().getFirstName();
        teacherLastName = student.getTeacher().getLastName();
        teacherEmail = student.getTeacher().getEmail();
    }

    return new StudentResponse(
            student.getId(),
            student.getEnrollMeantType(),
            student.getFirstName(),
            student.getMiddleName(),
            student.getLastName(),
            student.getGender(),
            student.getWhatsappNumber(),
            student.getDob(),
            student.getAddmissionDate(),
            student.getStd(),
            student.getCurrentLevel(),
            student.getCenter(),
            student.getState(),
            student.getDistrict(),
            student.getAddress(),
            student.getCity(),
            student.getEmail(),
            teacherId,
            teacherFirstName,
            teacherLastName,
            teacherEmail
    );
}



    // updateStatus

    public Student udpateStatus(int id, String status) {
    	Student cab = this.studentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Cart not found"));
    	cab.setStatus(status);
		    return studentRepo.save(cab);
    }


    // marks entry 
    public Student updateLevelWiseMark(int studentId, Map<String, Integer> updatedMarks) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        student.getLevelWiseMark().putAll(updatedMarks);

        return studentRepo.save(student);
    }
}
