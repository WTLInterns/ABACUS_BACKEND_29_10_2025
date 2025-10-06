package com.example.Abacus.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Level;
import com.example.Abacus.Repo.LevelRepo;

@Service
public class LevelService {
    

    @Autowired
    private LevelRepo levelRepo;

    public Level createLevel(Level level){
        // Convert name to uppercase before saving
        level.setName(level.getName().toUpperCase());
        return this.levelRepo.save(level);
    }

    public List<Level> getAllLevels(){
        // Convert all names to uppercase when retrieving
        return this.levelRepo.findAll().stream()
            .map(level -> {
                level.setName(level.getName().toUpperCase());
                return level;
            })
            .collect(Collectors.toList());
    }
    
    public Level getLevelById(int id) {
        Optional<Level> level = this.levelRepo.findById(id);
        if (level.isPresent()) {
            // Convert name to uppercase when retrieving
            level.get().setName(level.get().getName().toUpperCase());
        }
        return level.orElse(null);
    }
    
    public Level updateLevel(int id, Level level) {
        Optional<Level> existingLevel = this.levelRepo.findById(id);
        if (existingLevel.isPresent()) {
            Level updatedLevel = existingLevel.get();
            // Convert name to uppercase before updating
            updatedLevel.setName(level.getName().toUpperCase());
            return this.levelRepo.save(updatedLevel);
        }
        return null;
    }
    
    public void deleteLevel(int id) {
        this.levelRepo.deleteById(id);
    }
}