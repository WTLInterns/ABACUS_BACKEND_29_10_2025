package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Abacus.Model.Level;
import com.example.Abacus.Service.LevelService;

@RestController
@RequestMapping("/levels")
public class LevelController {
    

    @Autowired
    private LevelService levelService;
    

    @PostMapping
    public ResponseEntity<Level> createLevel(@RequestBody Level level){
        return new ResponseEntity<>(levelService.createLevel(level), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Level>> getAllLevels(){
        return ResponseEntity.ok(this.levelService.getAllLevels());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Level> getLevelById(@PathVariable int id) {
        Level level = this.levelService.getLevelById(id);
        if (level != null) {
            return ResponseEntity.ok(level);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Level> updateLevel(@PathVariable int id, @RequestBody Level level) {
        Level updatedLevel = this.levelService.updateLevel(id, level);
        if (updatedLevel != null) {
            return new ResponseEntity<>(updatedLevel, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLevel(@PathVariable int id) {
        this.levelService.deleteLevel(id);
        return ResponseEntity.ok("Level with ID " + id + " deleted successfully");
    }
}