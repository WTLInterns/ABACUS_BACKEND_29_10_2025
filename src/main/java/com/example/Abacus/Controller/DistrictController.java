package com.example.Abacus.Controller;

import com.example.Abacus.Model.District;
import com.example.Abacus.Service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping
    public ResponseEntity<List<District>> getAll() {
        return ResponseEntity.ok(districtService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getById(@PathVariable int id) {
        return ResponseEntity.ok(districtService.getById(id));
    }

    @PostMapping
    public ResponseEntity<District> create(@RequestBody District district) {
        return ResponseEntity.status(201).body(districtService.create(district));
    }

    @PutMapping("/{id}")
    public ResponseEntity<District> update(@PathVariable int id, @RequestBody District district) {
        return ResponseEntity.ok(districtService.update(id, district));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        districtService.delete(id);
        return ResponseEntity.ok("District deleted successfully");
    }
}
