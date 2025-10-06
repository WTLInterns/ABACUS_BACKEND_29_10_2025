package com.example.Abacus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Abacus.DTO.response.DistrictResponse;
import com.example.Abacus.Model.District;
import com.example.Abacus.Service.DistrictService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/districts")
public class DistrictController {
    
    @Autowired
    private DistrictService districtService;

    @PostMapping("/add/{stateId}")
    public ResponseEntity<DistrictResponse> createDistrict(@RequestBody District district, @PathVariable int stateId) {
        District createdDistrict = districtService.createDistrict(district, stateId);
        DistrictResponse districtResponse = new DistrictResponse(
            createdDistrict.getId(), 
            createdDistrict.getName(), 
            createdDistrict.getState().getId(), 
            createdDistrict.getState().getName()
        );
        return ResponseEntity.status(201).body(districtResponse);
    }
    
    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<DistrictResponse>> getDistrictsByState(@PathVariable int stateId) {
        List<District> districts = districtService.getAllDistricts(stateId);
        List<DistrictResponse> districtResponses = districts.stream()
                .map(district -> new DistrictResponse(
                    district.getId(), 
                    district.getName(), 
                    district.getState().getId(), 
                    district.getState().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(districtResponses);
    }
    
    // New endpoint to get districts by state name
    @GetMapping("/stateName/{stateName}")
    public ResponseEntity<List<DistrictResponse>> getDistrictsByStateName(@PathVariable String stateName) {
        List<District> districts = districtService.getDistrictsByStateName(stateName);
        List<DistrictResponse> districtResponses = districts.stream()
                .map(district -> new DistrictResponse(
                    district.getId(), 
                    district.getName(), 
                    district.getState().getId(), 
                    district.getState().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(districtResponses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponse> getDistrictById(@PathVariable int id) {
        District district = districtService.getDistrictById(id);
        DistrictResponse districtResponse = new DistrictResponse(
            district.getId(), 
            district.getName(), 
            district.getState().getId(), 
            district.getState().getName()
        );
        return ResponseEntity.ok(districtResponse);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DistrictResponse> updateDistrict(@PathVariable int id, @RequestBody District district) {
        district.setId(id); // Ensure the ID is set for update
        District updatedDistrict = districtService.updateDistrict(district);
        DistrictResponse districtResponse = new DistrictResponse(
            updatedDistrict.getId(), 
            updatedDistrict.getName(), 
            updatedDistrict.getState().getId(), 
            updatedDistrict.getState().getName()
        );
        return ResponseEntity.ok(districtResponse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDistrict(@PathVariable int id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.ok("District deleted successfully");
    }
}