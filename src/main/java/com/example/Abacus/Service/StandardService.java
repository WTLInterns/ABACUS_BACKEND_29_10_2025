package com.example.Abacus.Service;

import com.example.Abacus.Model.Standard;
import com.example.Abacus.Repo.StandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardService {

    private final StandardRepository standardRepository;

    public List<Standard> getAllStandards() {
        // Convert all names to uppercase when retrieving
        return standardRepository.findAll().stream()
            .map(standard -> {
                standard.setName(standard.getName().toUpperCase());
                return standard;
            })
            .collect(Collectors.toList());
    }

    public Standard getStandardById(Long id) {
        Standard standard = standardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standard not found with id: " + id));
        // Convert name to uppercase when retrieving
        standard.setName(standard.getName().toUpperCase());
        return standard;
    }

    public Standard createStandard(Standard standard) {
        // Convert name to uppercase before saving
        standard.setName(standard.getName().toUpperCase());
        standardRepository.findByName(standard.getName()).ifPresent(s -> {
            throw new RuntimeException("Standard already exists with name: " + standard.getName());
        });
        return standardRepository.save(standard);
    }

    public Standard updateStandard(Long id, Standard standard) {
        Standard existing = getStandardById(id);
        // Convert name to uppercase before updating
        existing.setName(standard.getName().toUpperCase());
        return standardRepository.save(existing);
    }

    public void deleteStandard(Long id) {
        Standard existing = getStandardById(id);
        standardRepository.delete(existing);
    }

    public String getStandardByName(String name) {
        String upperCaseName = name.toUpperCase();
        return standardRepository.findByName(upperCaseName)
                .map(Standard::getName)
                .map(String::toUpperCase)
                .orElseThrow(() -> new RuntimeException("Standard not found with name: " + upperCaseName));
    }
}