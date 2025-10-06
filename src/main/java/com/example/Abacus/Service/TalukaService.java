package com.example.Abacus.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.District;
import com.example.Abacus.Model.Taluka;
import com.example.Abacus.Repo.DistrictRepository;
import com.example.Abacus.Repo.TalukaRepository;
import com.example.Abacus.exception.ResourceNotFoundException;

@Service
public class TalukaService {
    
    @Autowired
    private TalukaRepository talukaRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public Taluka addTaluka(Taluka taluka, int districtId) {
        Optional<District> districtOpt = this.districtRepository.findById(districtId);
        if (!districtOpt.isPresent()) {
            throw new ResourceNotFoundException("District not found with id: " + districtId);
        }
        taluka.setDistrict(districtOpt.get());
        return talukaRepository.save(taluka);
    }

    public Taluka updateTaluka(Taluka taluka, int talukaId) {
        Optional<Taluka> existingOpt = this.talukaRepository.findById(talukaId);
        if (!existingOpt.isPresent()) {
            throw new ResourceNotFoundException("Taluka not found with id: " + talukaId);
        }
        
        Taluka existingTaluka = existingOpt.get();
        existingTaluka.setName(taluka.getName());
        // Preserve the district relationship
        if (taluka.getDistrict() != null) {
            existingTaluka.setDistrict(taluka.getDistrict());
        }
        return talukaRepository.save(existingTaluka);
    }

    public void deleteTaluka(int talukaId) {
        Optional<Taluka> talukaOpt = talukaRepository.findById(talukaId);
        if (!talukaOpt.isPresent()) {
            throw new ResourceNotFoundException("Taluka not found with id: " + talukaId);
        }
        talukaRepository.deleteById(talukaId);
    }

    public Taluka getTaluka(int talukaId) {
        Optional<Taluka> talukaOpt = talukaRepository.findById(talukaId);
        if (!talukaOpt.isPresent()) {
            throw new ResourceNotFoundException("Taluka not found with id: " + talukaId);
        }
        return talukaOpt.get();
    }

    public List<Taluka> getAllTalukas(int districtId) {
        Optional<District> districtOpt = districtRepository.findById(districtId);
        if (!districtOpt.isPresent()) {
            throw new ResourceNotFoundException("District not found with id: " + districtId);
        }
        return talukaRepository.findByDistrictId(districtId);
    }
    
    // New method to get talukas by district name
    public List<Taluka> getTalukasByDistrictName(String districtName) {
        Optional<District> districtOpt = districtRepository.findByName(districtName);
        if (!districtOpt.isPresent()) {
            throw new ResourceNotFoundException("District not found with name: " + districtName);
        }
        return talukaRepository.findByDistrictId(districtOpt.get().getId());
    }
}