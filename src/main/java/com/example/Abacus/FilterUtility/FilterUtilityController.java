package com.example.Abacus.FilterUtility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.Model.Student;

@RestController
public class FilterUtilityController {

    @Autowired
    private FilterService filterService;
    

    @GetMapping("/filter")
    public List<Student> filterStudents(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String taluka,
            @RequestParam(required = false) String city
    ) {
        return filterService.filterStudents(state, district, taluka, city);
    }


    @GetMapping("/status")
    public List<Student> getStudentsByStatus(@RequestParam String status) {
        return filterService.getStudentsByStatus(status);
    }
}
