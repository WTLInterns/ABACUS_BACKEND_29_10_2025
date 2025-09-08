package com.example.Abacus.Service;

import com.example.Abacus.Model.Taluka;
import com.example.Abacus.Repo.TalukaRepository;
import com.example.Abacus.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalukaService {

    private final TalukaRepository talukaRepository;

    public TalukaService(TalukaRepository talukaRepository) {
        this.talukaRepository = talukaRepository;
    }

    public List<Taluka> getAll() {
        return talukaRepository.findAll();
    }

    public Taluka getById(int id) {
        return talukaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Taluka not found with id: " + id));
    }

    public Taluka create(Taluka taluka) {
        return talukaRepository.save(taluka);
    }

    public Taluka update(int id, Taluka updated) {
        Taluka existing = getById(id);
        existing.setName(updated.getName());
        existing.setDistrict(updated.getDistrict());
        return talukaRepository.save(existing);
    }

    public void delete(int id) {
        Taluka existing = getById(id);
        talukaRepository.delete(existing);
    }
}
