package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.Model.Enquiry;
import com.example.Abacus.Service.EnquiryService;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {
    

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping
    public List<Enquiry> getAllEnquiries() {
        return enquiryService.getAllEnquiries();
    }

    @GetMapping("/{id}")
    public Enquiry getEnquiryById(@PathVariable int id) {
        return enquiryService.getEnquiryById(id);
    }

    @PostMapping
    public Enquiry saveEnquiry(@RequestBody Enquiry enquiry) {
        return enquiryService.saveEnquiry(enquiry);
    }

    @DeleteMapping("/{id}")
    public void deleteEnquiry(@PathVariable int id) {
        enquiryService.deleteEnquiry(id);
    }
}
