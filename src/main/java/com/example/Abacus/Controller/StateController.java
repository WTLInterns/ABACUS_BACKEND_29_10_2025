package com.example.Abacus.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.DTO.response.StateResponse;
import com.example.Abacus.Model.State;
import com.example.Abacus.Service.StateService;

@RestController
@RequestMapping("/states")
@CrossOrigin(origins = "*")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/add/{countryId}")
    public ResponseEntity<StateResponse> createState(@RequestBody State state, @PathVariable int countryId){
        State createdState = this.stateService.createState(state, countryId);
        StateResponse stateResponse = new StateResponse(
            createdState.getId(), 
            createdState.getName(), 
            createdState.getCountry().getId(), 
            createdState.getCountry().getName()
        );
        return ResponseEntity.status(201).body(stateResponse);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<StateResponse>> getStatesByCountry(@PathVariable int countryId) {
        List<State> states = this.stateService.getAllStates(countryId);
        List<StateResponse> stateResponses = states.stream()
                .map(state -> new StateResponse(
                    state.getId(), 
                    state.getName(), 
                    state.getCountry().getId(), 
                    state.getCountry().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stateResponses);
    }
    
    // New endpoint to get states by country name
    @GetMapping("/countryName/{countryName}")
    public ResponseEntity<List<StateResponse>> getStatesByCountryName(@PathVariable String countryName) {
        List<State> states = this.stateService.getStatesByCountryName(countryName);
        List<StateResponse> stateResponses = states.stream()
                .map(state -> new StateResponse(
                    state.getId(), 
                    state.getName(), 
                    state.getCountry().getId(), 
                    state.getCountry().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stateResponses);
    }
}