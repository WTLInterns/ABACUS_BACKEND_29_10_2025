package com.example.Abacus.Service;

import com.example.Abacus.Model.District;
import com.example.Abacus.Repo.DistrictRepository;
import com.example.Abacus.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public List<District> getAll() {
        return districtRepository.findAll();
    }

    public District getById(int id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + id));
    }

    public District create(District district) {
        return districtRepository.save(district);
    }

    public District update(int id, District updated) {
        District existing = getById(id);
        existing.setName(updated.getName());
        existing.setState(updated.getState());
        return districtRepository.save(existing);
    }

    public void delete(int id) {
        District existing = getById(id);
        districtRepository.delete(existing);
    }
}
