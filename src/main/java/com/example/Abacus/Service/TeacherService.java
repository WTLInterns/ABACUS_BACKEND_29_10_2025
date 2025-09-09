package com.example.Abacus.Service;


import com.example.Abacus.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Abacus.DTO.requests.TeacherRequests;
import com.example.Abacus.DTO.response.TeacherResponse;
import com.example.Abacus.Model.MasterAdmin;
import com.example.Abacus.Model.Payment;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.MasterAdminRepo;
import com.example.Abacus.Repo.PaymentRepo;
import com.example.Abacus.Repo.TeacherRepo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    

    @Autowired
    private TeacherRepo teacherRepository;

    @Autowired
    private MasterAdminRepo masterAdminRepository;

    

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private ReceiptService receiptService;

    private StudentRepo studentRepo;

    // CREATE
    public TeacherResponse saveTeacher(TeacherRequests request, int masterAdminId) {
    Teacher existingTeacher = teacherRepository
        .findByFirstNameAndLastName(request.getFirstName(), request.getLastName());
    if (existingTeacher != null) {
        throw new IllegalArgumentException("Teacher name already registered");
    }

    if (teacherRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already registered");
    }

    MasterAdmin masterAdmin = masterAdminRepository.findById(masterAdminId)
        .orElseThrow(() -> new IllegalArgumentException("Master Admin not found with id: " + masterAdminId));

    Teacher teacher = new Teacher();
    teacher.setFirstName(request.getFirstName());
    teacher.setLastName(request.getLastName());
    teacher.setEmail(request.getEmail());
    teacher.setPassword(request.getPassword());
    teacher.setRole("TEACHER");
    teacher.setFees(15000L);
    teacher.setRemainingAmount(15000L);
    teacher.setMasterAdmin(masterAdmin);

    Teacher savedTeacher = teacherRepository.save(teacher);

    if (request.getPaid() != null && request.getPaid() > 0) {
        Long paid = request.getPaid();
        Long totalFees = savedTeacher.getFees() != null ? savedTeacher.getFees() : 0L;
        Long remaining = totalFees - paid;

        Payment payment = new Payment();
        payment.setReceiptNo("REC" + System.currentTimeMillis());
        payment.setPaymentMode(request.getPaymentType());
        payment.setFees(totalFees);
        payment.setPaid(paid);
        payment.setRemainingAmount(remaining);
        payment.setTeacher(savedTeacher);

        paymentRepo.save(payment);

        savedTeacher.setRemainingAmount(remaining);
        teacherRepository.save(savedTeacher);

        receiptService.generateReceipt(payment);
    }

    return mapToResponse(savedTeacher);
}



    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TeacherResponse getTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
        return mapToResponse(teacher);
    }

    public TeacherResponse updateTeacher(int id, TeacherRequests request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(request.getPassword());

        

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponse(updatedTeacher);
    }

    public void deleteTeacher(int id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
        if (studentRepo.existsByTeacher(teacher)) {
            throw new IllegalStateException("Cannot delete teacher with linked students");
        }
        
        // teacherRepository.deleteById(id);
        
        teacherRepository.delete(teacher);
    }

    public List<TeacherResponse> getTeachersByMasterAdmin(int masterAdminId) {
        MasterAdmin masterAdmin = masterAdminRepository.findById(masterAdminId)
                .orElseThrow(() -> new IllegalArgumentException("Master Admin not found with id: " + masterAdminId));
        
        return teacherRepository.findByMasterAdmin(masterAdmin).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TeacherResponse mapToResponse(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        
        if (teacher.getMasterAdmin() != null) {
            response.setMasterAdminName(teacher.getMasterAdmin().getFirstName() + " " + teacher.getMasterAdmin().getLastName());
        }
        
        return response;
    }

    // public Teacher udpateTeacherFees(int id, Long paidAmount){
    //     Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Teacher not found"));
    //     teacher.setPaid(teacher.getPaid() + paidAmount);
    //     teacher.setRemainingAmount(teacher.getFees() - teacher.getPaid());
    //     return teacherRepository.save(teacher);
    // }


}
