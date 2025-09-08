package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Abacus.Model.Competition;
import com.example.Abacus.Service.CompetitionService;

@RestController
@RequestMapping("/competition")
public class CompetitionController {
    

    @Autowired
    private CompetitionService CompetitionService;;

    @PostMapping("/addCompetition")
    public Competition createCompetition(@RequestBody Competition competition){
        return this.CompetitionService.createCompetition(competition);
    
    }

    @GetMapping("/getAllCompetition")
    public List<Competition> getAllCompetitions(){
        return this.CompetitionService.getAllCompetitions();
    }

    @GetMapping("/{id}/getAllCompiById")
    public Competition getCompetitionById(@PathVariable int id){
        return this.CompetitionService.getCompetitionById(id);
    }

    @PutMapping("/updateCompetition/{id}")
    public Competition updateCompetition(@PathVariable int id, @RequestBody Competition competition){
        return this.CompetitionService.updateCompetition(id, competition);

    }

    @DeleteMapping("/deleteCompetition/{id}")
    public void deleteCompetition(@PathVariable int id){
        this.CompetitionService.deleteCompetition(id);
}




}
