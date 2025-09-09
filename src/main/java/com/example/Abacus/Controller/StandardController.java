package com.example.Abacus.Controller;

import com.example.Abacus.Model.Standard;
import com.example.Abacus.Service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standards")
@RequiredArgsConstructor
public class StandardController {

    private final StandardService standardService;

    @GetMapping
    public ResponseEntity<List<Standard>> getAllStandards() {
        return ResponseEntity.ok(standardService.getAllStandards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Standard> getStandardById(@PathVariable Long id) {
        return ResponseEntity.ok(standardService.getStandardById(id));
    }

    @GetMapping("/getStdByName/{name}")
    public ResponseEntity<String> getStandardByName(@PathVariable String name){
        return ResponseEntity.ok(standardService.getStandardByName(name));
    }

    @PostMapping
    public ResponseEntity<Standard> createStandard(@RequestBody Standard standard) {
        return new ResponseEntity<>(standardService.createStandard(standard), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Standard> updateStandard(@PathVariable Long id, @RequestBody Standard standard) {
        return ResponseEntity.ok(standardService.updateStandard(id, standard));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStandard(@PathVariable Long id) {
        standardService.deleteStandard(id);
        return ResponseEntity.ok("Standard deleted successfully!");
    }
}
