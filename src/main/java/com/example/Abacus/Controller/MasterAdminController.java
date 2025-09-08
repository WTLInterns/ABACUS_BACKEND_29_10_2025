package com.example.Abacus.Controller;

import com.example.Abacus.DTO.requests.TeacherRequests;
import com.example.Abacus.DTO.response.TeacherResponse;
import com.example.Abacus.Service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterAdminController {

    private final TeacherService teacherService;

    public MasterAdminController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/register/{masterAdminId}")
    public ResponseEntity<TeacherResponse> createTeacher(@RequestBody TeacherRequests request,
                                                         @PathVariable int masterAdminId) {
        return ResponseEntity.status(201).body(teacherService.saveTeacher(request, masterAdminId));
    }
}
