package com.example.Abacus.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.response.CompetitionResponse;
import com.example.Abacus.Model.Competition;
import com.example.Abacus.Model.Student;
import com.example.Abacus.Repo.CompetitionRepo;
import com.example.Abacus.Repo.StudentRepo;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepo competitionRepository;

    @Autowired
    private StudentRepo studentRepo;
    
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

// Method to assign a student to a competition (one-to-many)
public Competition assignStudentToCompetition(int studentId, int competitionId) {
    // Find the student and competition
    Student student = studentRepo.findById(studentId)
        .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    
    Competition competition = competitionRepository.findById(competitionId)
        .orElseThrow(() -> new RuntimeException("Competition not found with id: " + competitionId));
    
    // Assign competition to student
    student.setCompetition(competition);
    
    // Save the student (this will update the relationship)
    studentRepo.save(student);
    
    return competition;
}

// Method to remove a student from a competition
public Competition removeStudentFromCompetition(int studentId) {
    // Find the student
    Student student = studentRepo.findById(studentId)
        .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    
    // Get the current competition
    Competition competition = student.getCompetition();
    
    // Remove competition from student
    student.setCompetition(null);
    
    // Save the student (this will update the relationship)
    studentRepo.save(student);
    
    return competition;
}

// Method to convert Competition entity to CompetitionResponse DTO
public CompetitionResponse convertToCompetitionResponse(Competition competition) {
    CompetitionResponse response = new CompetitionResponse();
    response.setId(competition.getId());
    response.setCompetitionName(competition.getCompetitionName());
    response.setHeading(competition.getHeading());
    response.setDescription(competition.getDescription());
    response.setRegistrationLastDate(competition.getRegistrationLastDate());
    response.setStartDate(competition.getStartDate());
    response.setEndDate(competition.getEndDate());
    response.setStatus(competition.getStatus());
    return response;
}

}