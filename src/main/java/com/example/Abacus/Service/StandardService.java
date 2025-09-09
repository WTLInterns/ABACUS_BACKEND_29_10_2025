package com.example.Abacus.Service;

import com.example.Abacus.Model.Standard;
import com.example.Abacus.Repo.StandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardService {

    private final StandardRepository standardRepository;

    public List<Standard> getAllStandards() {
        return standardRepository.findAll();
    }

    public Standard getStandardById(Long id) {
        return standardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standard not found with id: " + id));
    }

    public Standard createStandard(Standard standard) {
        standardRepository.findByName(standard.getName()).ifPresent(s -> {
            throw new RuntimeException("Standard already exists with name: " + standard.getName());
        });
        return standardRepository.save(standard);
    }

    public Standard updateStandard(Long id, Standard standard) {
        Standard existing = getStandardById(id);
        existing.setName(standard.getName());
        return standardRepository.save(existing);
    }

    public void deleteStandard(Long id) {
        Standard existing = getStandardById(id);
        standardRepository.delete(existing);
    }

    public String getStandardByName(String name) {
        return standardRepository.findByName(name)
                .map(Standard::getName)
                .orElseThrow(() -> new RuntimeException("Standard not found with name: " + name));
    }

}
