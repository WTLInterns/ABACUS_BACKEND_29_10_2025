package com.example.Abacus.Service;

import com.example.Abacus.utility.RegisterNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.StudentRequest;
import com.example.Abacus.DTO.response.CompetitionResponse;
import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.Model.Competition;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.CompetitionRepo;
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

    @Autowired
    private CompetitionRepo competitionRepo;

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
                () -> new IllegalArgumentException("Teacher not found with userId: " + teacherId)
        );
        if (!"TEACHER".equals(teacher.getRole())) {
            throw new IllegalArgumentException("Provided userId does not belong to a TEACHER");
        }

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
        student.setPinCode(request.getPinCode());
        student.setEnrollMeantType(request.getEnrollMeantType());
        student.setStatus("PENDING"); // Automatically set status to PENDING
        student.setTeacher(teacher);
        student.setCountry(request.getCountry());

        // Automatically generate registerNo
        student.setRegisterNo(RegisterNoGenerator.generateRegisterNo());

        Student saved = studentRepo.save(student);
        
        // Create StudentResponse object and set values directly
        StudentResponse response = new StudentResponse();
        response.setId(saved.getId());
        response.setEnrollMeantType(saved.getEnrollMeantType());
        response.setFirstName(saved.getFirstName());
        response.setMiddleName(saved.getMiddleName());
        response.setLastName(saved.getLastName());
        response.setGender(saved.getGender());
        response.setWhatsappNumber(saved.getWhatsappNumber());
        response.setDob(saved.getDob());
        response.setAddmissionDate(saved.getAddmissionDate());
        response.setStd(saved.getStd());
        response.setCurrentLevel(saved.getCurrentLevel());
        response.setCenter(saved.getCenter());
        response.setState(saved.getState());
        response.setDistrict(saved.getDistrict());
        response.setAddress(saved.getAddress());
        response.setCity(saved.getCity());
        response.setEmail(saved.getEmail());
        response.setPinCode(saved.getPinCode());
        response.setTaluka(saved.getTaluka());
        response.setCountry(saved.getCountry());
        response.setStatus(saved.getStatus());
        response.setLevelWiseMark(saved.getLevelWiseMark());
        
        // Set competition (singular in one-to-many relationship)
        if (saved.getCompetition() != null) {
            CompetitionResponse competitionResponse = convertToCompetitionResponse(saved.getCompetition());
            response.setCompetitions(List.of(competitionResponse));
        }
        
        if (saved.getTeacher() != null) {
            response.setTeacherId(saved.getTeacher().getId());
            response.setTeacherFirstName(saved.getTeacher().getFirstName());
            response.setTeacherLastName(saved.getTeacher().getLastName());
            response.setTeacherEmail(saved.getTeacher().getEmail());
            // Set branch names from teacher
            response.setBranchNames(saved.getTeacher().getBranchName());
        }

        return response;
    }

    public List<StudentResponse> getStudentsByTeacherUserId(int teacherId) {
        return studentRepo.findByTeacherId(teacherId)
                .stream()
                .map(student -> {
                    StudentResponse response = new StudentResponse();
                    response.setId(student.getId());
                    response.setEnrollMeantType(student.getEnrollMeantType());
                    response.setFirstName(student.getFirstName());
                    response.setMiddleName(student.getMiddleName());
                    response.setLastName(student.getLastName());
                    response.setGender(student.getGender());
                    response.setWhatsappNumber(student.getWhatsappNumber());
                    response.setDob(student.getDob());
                    response.setAddmissionDate(student.getAddmissionDate());
                    response.setStd(student.getStd());
                    response.setCurrentLevel(student.getCurrentLevel());
                    response.setCenter(student.getCenter());
                    response.setState(student.getState());
                    response.setDistrict(student.getDistrict());
                    response.setAddress(student.getAddress());
                    response.setCity(student.getCity());
                    response.setEmail(student.getEmail());
                    response.setCountry(student.getCountry());
                    response.setStatus(student.getStatus());
                    response.setLevelWiseMark(student.getLevelWiseMark());
                    
                    // Set competition (singular in one-to-many relationship)
                    if (student.getCompetition() != null) {
                        CompetitionResponse competitionResponse = convertToCompetitionResponse(student.getCompetition());
                        response.setCompetitions(List.of(competitionResponse));
                    }
                    
                    if (student.getTeacher() != null) {
                        response.setTeacherId(student.getTeacher().getId());
                        response.setTeacherFirstName(student.getTeacher().getFirstName());
                        response.setTeacherLastName(student.getTeacher().getLastName());
                        response.setTeacherEmail(student.getTeacher().getEmail());
                        // Set branch names from teacher
                        response.setBranchNames(student.getTeacher().getBranchName());
                    }
                    
                    return response;
                })
                .collect(Collectors.toList());
    }

    // GET only approved students by teacher user ID
    public List<StudentResponse> getApprovedStudentsByTeacherUserId(int teacherId) {
        return studentRepo.findByTeacherId(teacherId)
                .stream()
                .filter(student -> "APPROVED".equals(student.getStatus()))
                .map(student -> {
                    StudentResponse response = new StudentResponse();
                    response.setId(student.getId());
                    response.setEnrollMeantType(student.getEnrollMeantType());
                    response.setFirstName(student.getFirstName());
                    response.setMiddleName(student.getMiddleName());
                    response.setLastName(student.getLastName());
                    response.setGender(student.getGender());
                    response.setWhatsappNumber(student.getWhatsappNumber());
                    response.setDob(student.getDob());
                    response.setAddmissionDate(student.getAddmissionDate());
                    response.setStd(student.getStd());
                    response.setCurrentLevel(student.getCurrentLevel());
                    response.setCenter(student.getCenter());
                    response.setState(student.getState());
                    response.setDistrict(student.getDistrict());
                    response.setAddress(student.getAddress());
                    response.setCity(student.getCity());
                    response.setEmail(student.getEmail());
                    response.setCountry(student.getCountry());
                    response.setStatus(student.getStatus());
                    response.setLevelWiseMark(student.getLevelWiseMark());
                    
                    // Set competition (singular in one-to-many relationship)
                    if (student.getCompetition() != null) {
                        CompetitionResponse competitionResponse = convertToCompetitionResponse(student.getCompetition());
                        response.setCompetitions(List.of(competitionResponse));
                    }
                    
                    if (student.getTeacher() != null) {
                        response.setTeacherId(student.getTeacher().getId());
                        response.setTeacherFirstName(student.getTeacher().getFirstName());
                        response.setTeacherLastName(student.getTeacher().getLastName());
                        response.setTeacherEmail(student.getTeacher().getEmail());
                        // Set branch names from teacher
                        response.setBranchNames(student.getTeacher().getBranchName());
                    }
                    
                    return response;
                })
                .collect(Collectors.toList());
    }

    // READ by ID
    public StudentResponse getStudentById(int id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
                
        // Create StudentResponse object and set values directly
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setEnrollMeantType(student.getEnrollMeantType());
        response.setFirstName(student.getFirstName());
        response.setMiddleName(student.getMiddleName());
        response.setLastName(student.getLastName());
        response.setGender(student.getGender());
        response.setWhatsappNumber(student.getWhatsappNumber());
        response.setDob(student.getDob());
        response.setAddmissionDate(student.getAddmissionDate());
        response.setStd(student.getStd());
        response.setCurrentLevel(student.getCurrentLevel());
        response.setCenter(student.getCenter());
        response.setState(student.getState());
        response.setDistrict(student.getDistrict());
        response.setAddress(student.getAddress());
        response.setCity(student.getCity());
        response.setEmail(student.getEmail());
        response.setPinCode(student.getPinCode());
        response.setTaluka(student.getTaluka());
        response.setCountry(student.getCountry());
        response.setStatus(student.getStatus());
        response.setLevelWiseMark(student.getLevelWiseMark());
        
        // Set competition (singular in one-to-many relationship)
        if (student.getCompetition() != null) {
            CompetitionResponse competitionResponse = convertToCompetitionResponse(student.getCompetition());
            response.setCompetitions(List.of(competitionResponse));
        }
        
        if (student.getTeacher() != null) {
            response.setTeacherId(student.getTeacher().getId());
            response.setTeacherFirstName(student.getTeacher().getFirstName());
            response.setTeacherLastName(student.getTeacher().getLastName());
            response.setTeacherEmail(student.getTeacher().getEmail());
            // Set branch names from teacher
            response.setBranchNames(student.getTeacher().getBranchName());
        }

        return response;
    }

    // READ all
    public List<StudentResponse> getAllStudents() {
        return studentRepo.findAll().stream()
                .map(student -> {
                    StudentResponse response = new StudentResponse();
                    response.setId(student.getId());
                    response.setEnrollMeantType(student.getEnrollMeantType());
                    response.setFirstName(student.getFirstName());
                    response.setMiddleName(student.getMiddleName());
                    response.setLastName(student.getLastName());
                    response.setGender(student.getGender());
                    response.setWhatsappNumber(student.getWhatsappNumber());
                    response.setDob(student.getDob());
                    response.setAddmissionDate(student.getAddmissionDate());
                    response.setStd(student.getStd());
                    response.setCurrentLevel(student.getCurrentLevel());
                    response.setCenter(student.getCenter());
                    response.setState(student.getState());
                    response.setDistrict(student.getDistrict());
                    response.setAddress(student.getAddress());
                    response.setCity(student.getCity());
                    response.setEmail(student.getEmail());
                    response.setCountry(student.getCountry());
                    response.setStatus(student.getStatus());
                    response.setLevelWiseMark(student.getLevelWiseMark());
                    
                    // Set competition (singular in one-to-many relationship)
                    if (student.getCompetition() != null) {
                        CompetitionResponse competitionResponse = convertToCompetitionResponse(student.getCompetition());
                        response.setCompetitions(List.of(competitionResponse));
                    }
                    
                    if (student.getTeacher() != null) {
                        response.setTeacherId(student.getTeacher().getId());
                        response.setTeacherFirstName(student.getTeacher().getFirstName());
                        response.setTeacherLastName(student.getTeacher().getLastName());
                        response.setTeacherEmail(student.getTeacher().getEmail());
                        // Set branch names from teacher
                        response.setBranchNames(student.getTeacher().getBranchName());
                    }
                    
                    return response;
                })
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
        student.setPinCode(request.getPinCode());
        student.setEnrollMeantType(request.getEnrollMeantType());
        student.setCountry(request.getCountry());

        Student updatedStudent = studentRepo.save(student);
        
        // Create StudentResponse object and set values directly
        StudentResponse response = new StudentResponse();
        response.setId(updatedStudent.getId());
        response.setEnrollMeantType(updatedStudent.getEnrollMeantType());
        response.setFirstName(updatedStudent.getFirstName());
        response.setMiddleName(updatedStudent.getMiddleName());
        response.setLastName(updatedStudent.getLastName());
        response.setGender(updatedStudent.getGender());
        response.setWhatsappNumber(updatedStudent.getWhatsappNumber());
        response.setDob(updatedStudent.getDob());
        response.setAddmissionDate(updatedStudent.getAddmissionDate());
        response.setStd(updatedStudent.getStd());
        response.setCurrentLevel(updatedStudent.getCurrentLevel());
        response.setCenter(updatedStudent.getCenter());
        response.setState(updatedStudent.getState());
        response.setDistrict(updatedStudent.getDistrict());
        response.setAddress(updatedStudent.getAddress());
        response.setCity(updatedStudent.getCity());
        response.setEmail(updatedStudent.getEmail());
        response.setPinCode(updatedStudent.getPinCode());
        response.setCountry(updatedStudent.getCountry());
        response.setStatus(updatedStudent.getStatus());
        response.setLevelWiseMark(updatedStudent.getLevelWiseMark());
        
        // Set competition (singular in one-to-many relationship)
        if (updatedStudent.getCompetition() != null) {
            CompetitionResponse competitionResponse = convertToCompetitionResponse(updatedStudent.getCompetition());
            response.setCompetitions(List.of(competitionResponse));
        }
        
        if (updatedStudent.getTeacher() != null) {
            response.setTeacherId(updatedStudent.getTeacher().getId());
            response.setTeacherFirstName(updatedStudent.getTeacher().getFirstName());
            response.setTeacherLastName(updatedStudent.getTeacher().getLastName());
            response.setTeacherEmail(updatedStudent.getTeacher().getEmail());
            // Set branch names from teacher
            response.setBranchNames(updatedStudent.getTeacher().getBranchName());
        }

        return response;
    }
    
    // Helper method to convert Competition to CompetitionResponse
    private CompetitionResponse convertToCompetitionResponse(Competition competition) {
        CompetitionResponse response = new CompetitionResponse();
        response.setId(competition.getId());
        response.setCompetitionName(competition.getCompetitionName());
        response.setHeading(competition.getHeading());
        response.setDescription(competition.getDescription());
        response.setRegistrationLastDate(competition.getRegistrationLastDate());
        response.setStartDate(competition.getStartDate());
        response.setEndDate(competition.getEndDate());
        response.setStatus(competition.getStatus());
        return response;
    }

    public String deleteStudent(int id) {
        if (!studentRepo.existsById(id)) {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
        studentRepo.deleteById(id);
        return "Student deleted successfully with id: " + id;
    }

    // updateStatus
    public Student udpateStatus(int id, String status) {
        Student student = this.studentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Student not found"));
        student.setStatus(status);
        return studentRepo.save(student);
    }

    public Student promoteLevel(int id , String level){
        Student student = this.studentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Student not found"));
        student.setCurrentLevel(level);
        return studentRepo.save(student);
    }

    // Add this new method to get students by state
    public List<StudentResponse> getStudentsByState(String state) {
        return studentRepo.findAll().stream()
                .filter(student -> state.equals(student.getState()))
                .map(student -> {
                    StudentResponse response = new StudentResponse();
                    response.setId(student.getId());
                    response.setEnrollMeantType(student.getEnrollMeantType());
                    response.setFirstName(student.getFirstName());
                    response.setMiddleName(student.getMiddleName());
                    response.setLastName(student.getLastName());
                    response.setGender(student.getGender());
                    response.setWhatsappNumber(student.getWhatsappNumber());
                    response.setDob(student.getDob());
                    response.setAddmissionDate(student.getAddmissionDate());
                    response.setStd(student.getStd());
                    response.setCurrentLevel(student.getCurrentLevel());
                    response.setCenter(student.getCenter());
                    response.setState(student.getState());
                    response.setDistrict(student.getDistrict());
                    response.setAddress(student.getAddress());
                    response.setCity(student.getCity());
                    response.setEmail(student.getEmail());
                    response.setCountry(student.getCountry());
                    response.setStatus(student.getStatus());
                    response.setLevelWiseMark(student.getLevelWiseMark());
                    
                    // Set competition (singular in one-to-many relationship)
                    if (student.getCompetition() != null) {
                        CompetitionResponse competitionResponse = convertToCompetitionResponse(student.getCompetition());
                        response.setCompetitions(List.of(competitionResponse));
                    }
                    
                    if (student.getTeacher() != null) {
                        response.setTeacherId(student.getTeacher().getId());
                        response.setTeacherFirstName(student.getTeacher().getFirstName());
                        response.setTeacherLastName(student.getTeacher().getLastName());
                        response.setTeacherEmail(student.getTeacher().getEmail());
                        // Set branch names from teacher
                        response.setBranchNames(student.getTeacher().getBranchName());
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }

    // marks entry 
    public Student updateLevelWiseMark(int studentId, Map<String, Integer> updatedMarks) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        student.getLevelWiseMark().putAll(updatedMarks);

        return studentRepo.save(student);
    }
    
    public Student getStudentEntityById(int id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
    }
}