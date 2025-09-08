package com.example.Abacus.Controller;


import com.example.Abacus.Model.Country;
import com.example.Abacus.Service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable int id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody Country country) {
        return ResponseEntity.status(201).body(countryService.create(country));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable int id, @RequestBody Country country) {
        return ResponseEntity.ok(countryService.update(id, country));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        countryService.delete(id);
        return ResponseEntity.ok("Country deleted successfully");
    }
}
