package com.example.Abacus.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Country;
import com.example.Abacus.Model.State;
import com.example.Abacus.Repo.CountryRepository;
import com.example.Abacus.Repo.StateRepository;
import com.example.Abacus.exception.ResourceNotFoundException;

@Service
public class StateService {
    
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    public State createState(State state, int countryId ){
        Optional<Country> countryOpt = countryRepository.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new ResourceNotFoundException("Country not found with id: " + countryId);
        }
        state.setCountry(countryOpt.get());
        return stateRepository.save(state);
    }

    public State updateState(int id, int countryId){ 
        Optional<State> stateOpt = stateRepository.findById(id);
        if (!stateOpt.isPresent()) {
            throw new ResourceNotFoundException("State not found with id: " + id);
        }
        
        Optional<Country> countryOpt = countryRepository.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new ResourceNotFoundException("Country not found with id: " + countryId);
        }
        
        State state = stateOpt.get();
        state.setCountry(countryOpt.get());
        return stateRepository.save(state);
    }

    public void deleteState(int id){ 
        Optional<State> stateOpt = stateRepository.findById(id);
        if (!stateOpt.isPresent()) {
            throw new ResourceNotFoundException("State not found with id: " + id);
        }
        stateRepository.deleteById(id);
    }

    public List<State> getAllStates(int countryId){ 
        Optional<Country> countryOpt = countryRepository.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new ResourceNotFoundException("Country not found with id: " + countryId);
        }
        return stateRepository.findByCountryId(countryId);
    }
    
    public State getStateById(int id) {
        Optional<State> stateOpt = stateRepository.findById(id);
        if (!stateOpt.isPresent()) {
            throw new ResourceNotFoundException("State not found with id: " + id);
        }
        return stateOpt.get();
    }
    
    // New method to get states by country name
    public List<State> getStatesByCountryName(String countryName) {
        Optional<Country> countryOpt = countryRepository.findByName(countryName);
        if (!countryOpt.isPresent()) {
            throw new ResourceNotFoundException("Country not found with name: " + countryName);
        }
        return stateRepository.findByCountryId(countryOpt.get().getId());
    }
}