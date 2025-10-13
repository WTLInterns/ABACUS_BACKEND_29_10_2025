package com.example.Abacus.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.DTO.response.CompetitionResponse;
import com.example.Abacus.Model.Competition;
import com.example.Abacus.Service.CompetitionService;

@RestController
@RequestMapping("/competition")
public class CompetitionController {
    

    @Autowired
    private CompetitionService CompetitionService;;

    @PostMapping("/addCompetition")
    public CompetitionResponse createCompetition(@RequestBody Competition competition){
        Competition createdCompetition = this.CompetitionService.createCompetition(competition);
        return this.CompetitionService.convertToCompetitionResponse(createdCompetition);
    }

    @GetMapping("/getAllCompetition")
    public List<CompetitionResponse> getAllCompetitions(){
        return this.CompetitionService.getAllCompetitions().stream()
                .map(this.CompetitionService::convertToCompetitionResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/getAllCompiById")
    public CompetitionResponse getCompetitionById(@PathVariable int id){
        Competition competition = this.CompetitionService.getCompetitionById(id);
        return this.CompetitionService.convertToCompetitionResponse(competition);
    }

    @PutMapping("/updateCompetition/{id}")
    public CompetitionResponse updateCompetition(@PathVariable int id, @RequestBody Competition competition){
        Competition updatedCompetition = this.CompetitionService.updateCompetition(id, competition);
        return this.CompetitionService.convertToCompetitionResponse(updatedCompetition);
    }

    @DeleteMapping("/deleteCompetition/{id}")
    public void deleteCompetition(@PathVariable int id){
        this.CompetitionService.deleteCompetition(id);
    }
    
    // Endpoint to assign a student to a competition
    @PostMapping("/assignStudent/{studentId}/{competitionId}")
    public CompetitionResponse assignStudent(@PathVariable int studentId, @PathVariable int competitionId) {
        Competition competition = this.CompetitionService.assignStudentToCompetition(studentId, competitionId);
        return this.CompetitionService.convertToCompetitionResponse(competition);
    }
    
    // Endpoint to remove a student from a competition
    @DeleteMapping("/removeStudent/{studentId}")
    public CompetitionResponse removeStudent(@PathVariable int studentId) {
        Competition competition = this.CompetitionService.removeStudentFromCompetition(studentId);
        return this.CompetitionService.convertToCompetitionResponse(competition);
    }

}