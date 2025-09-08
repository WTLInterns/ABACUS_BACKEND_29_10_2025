package com.example.Abacus.Service;


import com.example.Abacus.Model.Country;
import com.example.Abacus.Repo.CountryRepository;
import com.example.Abacus.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    public Country getById(int id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + id));
    }

    public Country create(Country country) {
        return countryRepository.save(country);
    }

    public Country update(int id, Country updated) {
        Country existing = getById(id);
        existing.setName(updated.getName());
        return countryRepository.save(existing);
    }

    public void delete(int id) {
        Country existing = getById(id);
        countryRepository.delete(existing);
    }
}
