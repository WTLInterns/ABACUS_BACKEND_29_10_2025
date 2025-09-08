package com.example.Abacus.Controller;


import com.example.Abacus.Model.State;
import com.example.Abacus.Service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<State>> getAll() {
        return ResponseEntity.ok(stateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getById(@PathVariable int id) {
        return ResponseEntity.ok(stateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<State> create(@RequestBody State state) {
        return ResponseEntity.status(201).body(stateService.create(state));
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable int id, @RequestBody State state) {
        return ResponseEntity.ok(stateService.update(id, state));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        stateService.delete(id);
        return ResponseEntity.ok("State deleted successfully");
    }
}
