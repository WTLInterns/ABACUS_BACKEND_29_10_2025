package com.example.Abacus.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.District;
import com.example.Abacus.Model.State;
import com.example.Abacus.Repo.DistrictRepository;
import com.example.Abacus.Repo.StateRepository;
import com.example.Abacus.exception.ResourceNotFoundException;

@Service
public class DistrictService {
    
    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private StateRepository stateRepository;

    public District createDistrict(District district, int stateId) {
        Optional<State> stateOpt = this.stateRepository.findById(stateId);
        if (!stateOpt.isPresent()) {
            throw new ResourceNotFoundException("State not found with id: " + stateId);
        }
        district.setState(stateOpt.get());
        return districtRepository.save(district);
    }

    public List<District> getAllDistricts(int stateId) {
        Optional<State> stateOpt = this.stateRepository.findById(stateId);
        if (!stateOpt.isPresent()) {
            throw new ResourceNotFoundException("State not found with id: " + stateId);
        }
        return districtRepository.findByStateId(stateId);
    }
    
    // New method to get districts by state name
    public List<District> getDistrictsByStateName(String stateName) {
        Optional<State> stateOpt = this.stateRepository.findByName(stateName);
        if (!stateOpt.isPresent()) {
            throw new ResourceNotFoundException("State not found with name: " + stateName);
        }
        return districtRepository.findByStateId(stateOpt.get().getId());
    }

    public District getDistrictById(int districtId) {
        Optional<District> districtOpt = districtRepository.findById(districtId);
        if (!districtOpt.isPresent()) {
            throw new ResourceNotFoundException("District not found with id: " + districtId);
        }
        return districtOpt.get();
    }

    public District updateDistrict(District district) {
        Optional<District> existingOpt = districtRepository.findById(district.getId());
        if (!existingOpt.isPresent()) {
            throw new ResourceNotFoundException("District not found with id: " + district.getId());
        }
        return districtRepository.save(district);
    }
    
    public void deleteDistrict(int districtId) {
        Optional<District> districtOpt = districtRepository.findById(districtId);
        if (!districtOpt.isPresent()) {
            throw new ResourceNotFoundException("District not found with id: " + districtId);
        }
        districtRepository.deleteById(districtId);
    }
}