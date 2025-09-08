package com.example.Abacus.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.SignupRequest;
import com.example.Abacus.DTO.requests.TeacherRequests;
import com.example.Abacus.DTO.response.TeacherResponse;
import com.example.Abacus.Model.MasterAdmin;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.MasterAdminRepo;
import com.example.Abacus.Repo.TeacherRepo;

@Service
public class TeacherService {
    

    @Autowired
    private TeacherRepo teacherRepository;

    @Autowired
    private MasterAdminRepo masterAdminRepository;


    public TeacherResponse saveTeacher(TeacherRequests request, int masterAdminId) {
    Teacher existingTeacher  = this.teacherRepository
        .findByFirstNameAndLastName(request.getFirstName(), request.getLastName());
    if (existingTeacher != null) {
        throw new IllegalArgumentException("Teacher already registered");
    }

    MasterAdmin masterAdmin = masterAdminRepository.findById(masterAdminId).orElseThrow(
        () -> new IllegalArgumentException("Master Admin not found with id: " + masterAdminId)
    );

    Teacher teacher = new Teacher();
    teacher.setFirstName(request.getFirstName());
    teacher.setLastName(request.getLastName());
    teacher.setEmail(request.getEmail());
    teacher.setPassword(request.getPassword());
    teacher.setMasterAdmin(masterAdmin);
    Teacher savedTeacher = teacherRepository.save(teacher);
    TeacherResponse response = new TeacherResponse();
    response.setId(savedTeacher.getId());
    response.setFirstName(savedTeacher.getFirstName());
    response.setLastName(savedTeacher.getLastName());
    response.setEmail(savedTeacher.getEmail());
    return response;
}

}
