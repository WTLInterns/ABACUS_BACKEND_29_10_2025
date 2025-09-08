package com.example.Abacus.Service;


import com.example.Abacus.Model.Center;
import com.example.Abacus.Repo.CenterRepository;
import com.example.Abacus.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {

    private final CenterRepository centerRepository;

    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public List<Center> getAll() {
        return centerRepository.findAll();
    }

    public Center getById(int id) {
        return centerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Center not found with id: " + id));
    }

    public Center create(Center center) {
        return centerRepository.save(center);
    }

    public Center update(int id, Center updated) {
        Center existing = getById(id);
        existing.setName(updated.getName());
        existing.setTaluka(updated.getTaluka());
        return centerRepository.save(existing);
    }

    public void delete(int id) {
        Center existing = getById(id);
        centerRepository.delete(existing);
    }
}
