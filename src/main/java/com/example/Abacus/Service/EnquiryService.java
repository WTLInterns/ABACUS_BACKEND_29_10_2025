package com.example.Abacus.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Enquiry;
import com.example.Abacus.Repo.EnquiryRepo;

@Service
public class EnquiryService {
    
    @Autowired
    private EnquiryRepo enquiryRepo;

    public List<Enquiry> getAllEnquiries() {
        return enquiryRepo.findAll();
    }

    public Enquiry getEnquiryById(int id) {
        return enquiryRepo.findById(id).orElse(null);
    }

    public Enquiry saveEnquiry(Enquiry enquiry) {
        return enquiryRepo.save(enquiry);
    }

    public void deleteEnquiry(int id) {
        enquiryRepo.deleteById(id);
    }
}
