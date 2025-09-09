package com.example.Abacus.Service;

import com.example.Abacus.Model.Foundation;
import com.example.Abacus.Repo.FoundationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FoundationService {

    private final FoundationRepository foundationRepository;

    public List<Foundation> getAllFoundations() {
        return foundationRepository.findAll();
    }

    public Foundation getFoundationById(Long id) {
        return foundationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foundation not found with id: " + id));
    }

    public Foundation getFoundationByName(String name) {
        return foundationRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Foundation not found with name: " + name));
    }

    public Foundation createFoundation(Foundation foundation) {
        foundationRepository.findByName(foundation.getName())
                .ifPresent(f -> { throw new RuntimeException("Foundation already exists with name: " + foundation.getName()); });
        return foundationRepository.save(foundation);
    }

    public Foundation updateFoundation(Long id, Foundation foundation) {
        Foundation existing = getFoundationById(id);
        existing.setName(foundation.getName());
        return foundationRepository.save(existing);
    }

    public void deleteFoundation(Long id) {
        Foundation existing = getFoundationById(id);
        foundationRepository.delete(existing);
    }
}
