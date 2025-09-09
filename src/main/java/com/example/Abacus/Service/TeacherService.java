package com.example.Abacus.Service;

import com.example.Abacus.Model.User;
import com.example.Abacus.Repo.UserRepo;
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
    private UserRepo userRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private ReceiptService receiptService;


    // CREATE
    public TeacherResponse saveTeacher(TeacherRequests request, int masterAdminId) {
    Teacher existingTeacher = teacherRepository
        .findByFirstNameAndLastName(request.getFirstName(), request.getLastName());
    if (existingTeacher != null) {
        throw new IllegalArgumentException("Teacher already registered");
    }

    if (userRepo.findByEmail(request.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already registered");
    }

    MasterAdmin masterAdmin = masterAdminRepository.findById(masterAdminId)
        .orElseThrow(() -> new IllegalArgumentException("Master Admin not found with id: " + masterAdminId));

    User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setRole(User.Role.TEACHER);
    User savedUser = userRepo.save(user);

    Teacher teacher = new Teacher();
    teacher.setFirstName(request.getFirstName());
    teacher.setLastName(request.getLastName());
    teacher.setEmail(request.getEmail());
    teacher.setPassword(request.getPassword());
    teacher.setRole(User.Role.TEACHER);
    teacher.setFees(15000L);
    teacher.setRemainingAmount(15000L); 
    teacher.setMasterAdmin(masterAdmin);
    teacher.setUser(savedUser);

    Teacher savedTeacher = teacherRepository.save(teacher);

    if (request.getPaid() != null && request.getPaid() > 0) {
        Payment payment = new Payment();
        payment.setReceiptNo("REC" + System.currentTimeMillis());
        payment.setPaymentMode(request.getPaymentType());
        payment.setFees(teacher.getFees());
        payment.setPaid(request.getPaid());
        payment.setRemainingAmount(teacher.getFees() - request.getPaid());
        payment.setTeacher(savedTeacher);

        paymentRepo.save(payment);

        savedTeacher.setRemainingAmount(teacher.getFees() - request.getPaid());
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

        if (teacher.getUser() != null) {
            User user = teacher.getUser();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            userRepo.save(user);
        }

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponse(updatedTeacher);
    }

    public void deleteTeacher(int id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
        
        if (teacher.getUser() != null) {
            userRepo.delete(teacher.getUser());
        }
        
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
