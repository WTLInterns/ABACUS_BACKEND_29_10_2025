package com.example.Abacus.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.DashboardResponse;
import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.InventoryRepo;
import com.example.Abacus.Repo.MasterAdminRepo;
import com.example.Abacus.Repo.StudentRepo;
import com.example.Abacus.Repo.TeacherRepo;

@Service
public class DashboardService {
    
    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private MasterAdminRepo masterAdminRepo;

    @Autowired
    private InventoryRepo inventoryRepo;

    public List<StudentResponse> getStudentByEnrollMentType(String enrollMentType, int teacherId) {
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getEnrollMeantType().equalsIgnoreCase(enrollMentType)
                    && student.getTeacher().getId() == teacherId)
            .map(student -> {
                StudentResponse response = new StudentResponse();
                response.setId(student.getId());
                response.setFirstName(student.getFirstName());
                response.setLastName(student.getLastName());
                response.setStatus(student.getStatus());
                response.setEmail(student.getEmail());
                response.setEnrollMeantType(student.getEnrollMeantType());
                response.setTeacherId(student.getTeacher().getId());
                response.setCurrentLevel(student.getCurrentLevel());
                return response;
            })
            .collect(Collectors.toList());
}

public String getStudentCountByTeacher(int teacherId){
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getTeacher().getId() == teacherId)
            .count() + "";
}


public String getStudentStatusCountByTeacherId(int teacher, String status){
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getTeacher().getId() == teacher && student.getStatus().equalsIgnoreCase(status))
            .count() + "";
}

public String getStudentEnrollMentTypeCountByTeacherId(int teacher, String enrollMentType){
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getTeacher().getId() == teacher && student.getEnrollMeantType().equalsIgnoreCase(enrollMentType))
            .count() + "";
}
// ----------------------------FOR ADMIN------------------------

public String getTeacherCountByMasterAdminId(int masterAdminId){
    return teacherRepo.findAll()
            .stream()
            .filter(teacher -> teacher.getMasterAdmin().getId() == masterAdminId)
            .count() + "";
}

public List<DashboardResponse> getStudentEnrollmentTypeCountWithTeacher(int masterAdminId, String enrollmentType) {
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getTeacher() != null &&
                               student.getTeacher().getMasterAdmin() != null &&
                               student.getTeacher().getMasterAdmin().getId() == masterAdminId &&
                               student.getEnrollMeantType().equalsIgnoreCase(enrollmentType))
            .collect(Collectors.groupingBy(student -> student.getTeacher()))
            .entrySet()
            .stream()
            .map(entry -> {
                var teacher = entry.getKey();
                var students = entry.getValue();

                DashboardResponse response = new DashboardResponse();
                response.setTeacherName(teacher.getFirstName() + " " + teacher.getLastName());
                response.setEmail(teacher.getEmail());
                response.setEnrollmentType(enrollmentType);
                response.setCount(String.valueOf(students.size()));
                response.setTeacherId(teacher.getId());  // Set the teacherId

                return response;
            })
            .collect(Collectors.toList());
}


public String getStudentCountByEnrollmentTypeByMasterAdminId(int masterAdminId, String enrollmentType){
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getTeacher() != null &&
                               student.getTeacher().getMasterAdmin() != null &&
                               student.getTeacher().getMasterAdmin().getId() == masterAdminId &&
                               student.getEnrollMeantType().equalsIgnoreCase(enrollmentType))
            .count() + "";

}

public String getStudentCountByStatusByMasterAdminId(int masterAdminId, String status){
    return studentRepo.findAll()
            .stream()
            .filter(student -> student.getTeacher() != null &&
                               student.getTeacher().getMasterAdmin() != null &&
                               student.getTeacher().getMasterAdmin().getId() == masterAdminId &&
                               student.getStatus().equalsIgnoreCase(status))
            .count() + "";
}


public String getInventoryCountByMasterAdminId(int masterAdminId){
    return this.inventoryRepo.findAll()
            .stream()
            .filter(inventory -> inventory.getMasterAdmin().getId() == masterAdminId)
            .count() + "";
}

}