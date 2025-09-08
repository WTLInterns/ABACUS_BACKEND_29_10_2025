package com.example.Abacus.Service;


import com.example.Abacus.Model.State;
import com.example.Abacus.Repo.StateRepository;
import com.example.Abacus.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> getAll() {
        return stateRepository.findAll();
    }

    public State getById(int id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("State not found with id: " + id));
    }

    public State create(State state) {
        return stateRepository.save(state);
    }

    public State update(int id, State updated) {
        State existing = getById(id);
        existing.setName(updated.getName());
        existing.setCountry(updated.getCountry());
        return stateRepository.save(existing);
    }

    public void delete(int id) {
        State existing = getById(id);
        stateRepository.delete(existing);
    }
}
