package com.example.Abacus.Controller;


import com.example.Abacus.Model.Taluka;
import com.example.Abacus.Service.TalukaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talukas")
public class TalukaController {

    private final TalukaService talukaService;

    public TalukaController(TalukaService talukaService) {
        this.talukaService = talukaService;
    }

    @GetMapping
    public ResponseEntity<List<Taluka>> getAll() {
        return ResponseEntity.ok(talukaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taluka> getById(@PathVariable int id) {
        return ResponseEntity.ok(talukaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Taluka> create(@RequestBody Taluka taluka) {
        return ResponseEntity.status(201).body(talukaService.create(taluka));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Taluka> update(@PathVariable int id, @RequestBody Taluka taluka) {
        return ResponseEntity.ok(talukaService.update(id, taluka));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        talukaService.delete(id);
        return ResponseEntity.ok("Taluka deleted successfully");
    }
}
