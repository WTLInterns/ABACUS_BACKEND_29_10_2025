package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.DTO.DashboardResponse;
import com.example.Abacus.DTO.response.StudentResponse;
import com.example.Abacus.Service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/by-enrollment-type/{teacherId}/{enrollMentType}")
    public List<StudentResponse> getStudentsByEnrollmentType(
            @PathVariable int teacherId,
            @PathVariable String enrollMentType) {

        return dashboardService.getStudentByEnrollMentType(enrollMentType, teacherId);
    }

    @GetMapping("/studentcount/{teacherId}")
    public String getStudentCount(@PathVariable int teacherId){
        return dashboardService.getStudentCountByTeacher(teacherId);
    }

    @GetMapping("/student-status-count/{teacherId}/{status}")
    public String getStudentStatusCount(@PathVariable int teacherId, @PathVariable String status){
        return dashboardService.getStudentStatusCountByTeacherId(teacherId, status);
    }

    @GetMapping("/studentEnrollMentTypeCount/{teacherId}/{status}")
    public String getStudentEnrollMentTypeCount(@PathVariable int teacherId, @PathVariable String status){
        return dashboardService.getStudentEnrollMentTypeCountByTeacherId(teacherId, status);
    }

    
// -------------------------MASTER_ADMIN----------------------------
    @GetMapping("/teacherCount/{masterAdminId}")
    public String getTeacherCountByMasterAdminId(@PathVariable int masterAdminId){
        return dashboardService.getTeacherCountByMasterAdminId(masterAdminId);
    }

    @GetMapping("/studentEnrollmentTypeCountWithTeacher/{masterAdminId}/{enrollmentType}")
    public List<DashboardResponse> getStudentEnrollmentTypeCountWithTeacher(@PathVariable int masterAdminId, @PathVariable String enrollmentType){
        return dashboardService.getStudentEnrollmentTypeCountWithTeacher(masterAdminId, enrollmentType);
    }

    @GetMapping("/studentCountByEnrollmentTypeByMasterAdminId/{masterAdminId}/{enrollmentType}")
    public String getStudentCountByEnrollmentTypeByMasterAdminId(@PathVariable int masterAdminId, @PathVariable String enrollmentType){
        return dashboardService.getStudentCountByEnrollmentTypeByMasterAdminId(masterAdminId, enrollmentType);
    }

    @GetMapping("/studentCountByStatusByMasterAdminId/{masterAdminId}/{status}")
    public String getStudentCountByStatusByMasterAdminId(@PathVariable int masterAdminId, @PathVariable String status){
        return dashboardService.getStudentCountByStatusByMasterAdminId(masterAdminId, status);
    }

    @GetMapping("/inventoryCountCount/{masterAdminId}")
    public String getInventoryCountCount(@PathVariable int masterAdminId){
        return dashboardService.getInventoryCountByMasterAdminId(masterAdminId);
    }

 
    
}
