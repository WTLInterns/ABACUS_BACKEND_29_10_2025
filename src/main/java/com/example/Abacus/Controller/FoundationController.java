package com.example.Abacus.Controller;

import com.example.Abacus.Model.Foundation;
import com.example.Abacus.Service.FoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foundations")
@RequiredArgsConstructor
public class FoundationController {

    private final FoundationService foundationService;

    @GetMapping
    public ResponseEntity<List<Foundation>> getAllFoundations() {
        return ResponseEntity.ok(foundationService.getAllFoundations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Foundation> getFoundationById(@PathVariable Long id) {
        return ResponseEntity.ok(foundationService.getFoundationById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Foundation> getFoundationByName(@PathVariable String name) {
        return ResponseEntity.ok(foundationService.getFoundationByName(name));
    }

    @PostMapping
    public ResponseEntity<Foundation> createFoundation(@RequestBody Foundation foundation) {
        return new ResponseEntity<>(foundationService.createFoundation(foundation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Foundation> updateFoundation(@PathVariable Long id, @RequestBody Foundation foundation) {
        return ResponseEntity.ok(foundationService.updateFoundation(id, foundation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoundation(@PathVariable Long id) {
        foundationService.deleteFoundation(id);
        return ResponseEntity.ok("Foundation deleted successfully!");
    }
}
