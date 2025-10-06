package com.example.Abacus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Abacus.DTO.response.TalukaResponse;
import com.example.Abacus.Model.Taluka;
import com.example.Abacus.Service.TalukaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/talukas")
public class TalukaController {
    
    @Autowired
    private TalukaService talukaService;

    @PostMapping("/add/{districtId}")
    public ResponseEntity<TalukaResponse> addTaluka(@RequestBody Taluka taluka, @PathVariable int districtId) {
        Taluka createdTaluka = talukaService.addTaluka(taluka, districtId);
        TalukaResponse talukaResponse = new TalukaResponse(
            createdTaluka.getId(), 
            createdTaluka.getName(), 
            createdTaluka.getDistrict().getId(), 
            createdTaluka.getDistrict().getName()
        );
        return ResponseEntity.status(201).body(talukaResponse);
    }
    
    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<TalukaResponse>> getTalukasByDistrict(@PathVariable int districtId) {
        List<Taluka> talukas = talukaService.getAllTalukas(districtId);
        List<TalukaResponse> talukaResponses = talukas.stream()
                .map(taluka -> new TalukaResponse(
                    taluka.getId(), 
                    taluka.getName(), 
                    taluka.getDistrict().getId(), 
                    taluka.getDistrict().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(talukaResponses);
    }
    
    // New endpoint to get talukas by district name
    @GetMapping("/districtName/{districtName}")
    public ResponseEntity<List<TalukaResponse>> getTalukasByDistrictName(@PathVariable String districtName) {
        List<Taluka> talukas = talukaService.getTalukasByDistrictName(districtName);
        List<TalukaResponse> talukaResponses = talukas.stream()
                .map(taluka -> new TalukaResponse(
                    taluka.getId(), 
                    taluka.getName(), 
                    taluka.getDistrict().getId(), 
                    taluka.getDistrict().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(talukaResponses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TalukaResponse> getTalukaById(@PathVariable int id) {
        Taluka taluka = talukaService.getTaluka(id);
        TalukaResponse talukaResponse = new TalukaResponse(
            taluka.getId(), 
            taluka.getName(), 
            taluka.getDistrict().getId(), 
            taluka.getDistrict().getName()
        );
        return ResponseEntity.ok(talukaResponse);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TalukaResponse> updateTaluka(@PathVariable int id, @RequestBody Taluka taluka) {
        Taluka updatedTaluka = talukaService.updateTaluka(taluka, id);
        TalukaResponse talukaResponse = new TalukaResponse(
            updatedTaluka.getId(), 
            updatedTaluka.getName(), 
            updatedTaluka.getDistrict().getId(), 
            updatedTaluka.getDistrict().getName()
        );
        return ResponseEntity.ok(talukaResponse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaluka(@PathVariable int id) {
        talukaService.deleteTaluka(id);
        return ResponseEntity.ok("Taluka deleted successfully");
    }
}