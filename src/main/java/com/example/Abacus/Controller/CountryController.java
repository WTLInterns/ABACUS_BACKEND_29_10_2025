package com.example.Abacus.Controller;


import com.example.Abacus.DTO.response.CountryResponse;
import com.example.Abacus.Model.Country;
import com.example.Abacus.Service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryResponse>> getAll() {
        List<Country> countries = countryService.getAll();
        List<CountryResponse> countryResponses = countries.stream()
                .map(country -> new CountryResponse(country.getId(), country.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(countryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getById(@PathVariable int id) {
        Country country = countryService.getById(id);
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getName());
        return ResponseEntity.ok(countryResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<CountryResponse> create(@RequestBody Country country) {
        Country createdCountry = countryService.create(country);
        CountryResponse countryResponse = new CountryResponse(createdCountry.getId(), createdCountry.getName());
        return ResponseEntity.status(201).body(countryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> update(@PathVariable int id, @RequestBody Country country) {
        Country updatedCountry = countryService.update(id, country);
        CountryResponse countryResponse = new CountryResponse(updatedCountry.getId(), updatedCountry.getName());
        return ResponseEntity.ok(countryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        countryService.delete(id);
        return ResponseEntity.ok("Country deleted successfully");
    }
}