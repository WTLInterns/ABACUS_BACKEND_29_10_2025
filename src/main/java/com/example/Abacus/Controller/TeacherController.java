package com.example.Abacus.Controller;

import com.example.Abacus.DTO.requests.TeacherRequests;
import com.example.Abacus.DTO.response.TeacherResponse;
import com.example.Abacus.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TeacherResponse> createTeacher(
            @RequestPart("data") TeacherRequests request,
            @RequestPart(value = "addharImage", required = false) MultipartFile addharImage,
            @RequestPart(value = "markshitImage", required = false) MultipartFile markshitImage,
            @RequestParam("masterAdminId") int masterAdminId) {
        return ResponseEntity.ok(teacherService.saveTeacherWithImages(request, masterAdminId, addharImage, markshitImage));
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(@PathVariable int id, @RequestBody TeacherRequests request) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher deleted successfully");
    }

    @GetMapping("/master-admin/{masterAdminId}")
    public ResponseEntity<List<TeacherResponse>> getTeachersByMasterAdmin(@PathVariable int masterAdminId) {
        return ResponseEntity.ok(teacherService.getTeachersByMasterAdmin(masterAdminId));
    }
}