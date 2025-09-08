package com.example.Abacus.Controller;

import com.example.Abacus.Model.Center;
import com.example.Abacus.Service.CenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/centers")
public class CenterController {

    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping
    public ResponseEntity<List<Center>> getAll() {
        return ResponseEntity.ok(centerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Center> getById(@PathVariable int id) {
        return ResponseEntity.ok(centerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Center> create(@RequestBody Center center) {
        return ResponseEntity.status(201).body(centerService.create(center));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Center> update(@PathVariable int id, @RequestBody Center center) {
        return ResponseEntity.ok(centerService.update(id, center));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        centerService.delete(id);
        return ResponseEntity.ok("Center deleted successfully");
    }
}
