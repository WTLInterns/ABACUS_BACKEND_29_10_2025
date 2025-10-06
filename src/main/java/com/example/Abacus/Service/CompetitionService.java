package com.example.Abacus.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.Model.Competition;
import com.example.Abacus.Repo.CompetitionRepo;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepo competitionRepository;
    
    public Competition createCompetition(Competition competition){
        return this.competitionRepository.save(competition);
}

public List<Competition> getAllCompetitions(){
    return this.competitionRepository.findAll();
}

public Competition getCompetitionById(int id){
    return this.competitionRepository.findById(id).orElse(null);
}

public Competition updateCompetition(int id, Competition competition){
    Competition existingCompetition = this.competitionRepository.findById(id).orElse(null);
    if(existingCompetition != null){
        existingCompetition.setCompetitionName(competition.getCompetitionName());
        existingCompetition.setHeading(competition.getHeading());
        existingCompetition.setDescription(competition.getDescription());
        existingCompetition.setRegistrationLastDate(competition.getRegistrationLastDate());
        existingCompetition.setStartDate(competition.getStartDate());
        existingCompetition.setEndDate(competition.getEndDate());
        existingCompetition.setStatus(competition.getStatus());
        return this.competitionRepository.save(existingCompetition);
    }
    return null;
}

public void deleteCompetition(int id){
    this.competitionRepository.deleteById(id);
}

}