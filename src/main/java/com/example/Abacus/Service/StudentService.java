package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.StudentRequest;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.StudentRepo;
import com.example.Abacus.Repo.TeacherRepo;

@Service
public class StudentService {
    

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;


    public StudentRequest createStudent(StudentRequest request, int teacherId){
        Student existingByName = studentRepo.findByFirstNameAndMiddleNameAndLastName(
            request.getFirstName(),
            request.getMiddleName(),
            request.getLastName()
    );
    if (existingByName != null) {
        throw new RuntimeException("Student already registered");
    }
    Teacher teacher = teacherRepo.findById(teacherId).orElse(null);
    if (teacher == null) {
        throw new RuntimeException("Teacher not found with id: " + teacherId);
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
    student.setStd(request.getStd());
    student.setState(request.getState());
    student.setDistrict(request.getDistrict());
    student.setCity(request.getCity());
    student.setEmail(request.getEmail());
    student.setTaluka(request.getTaluka());
    
student.setTeacher(teacher);
studentRepo.save(student);

    return request;

    }


}
