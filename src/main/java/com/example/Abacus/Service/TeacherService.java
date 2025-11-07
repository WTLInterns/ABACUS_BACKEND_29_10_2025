package com.example.Abacus.Service;

import com.example.Abacus.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

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
    teacher.setBranchName(request.getBranchName());
    
    // Set new fields from request
    teacher.setEducation(request.getEducation());
    teacher.setMarkshit(request.getMarkshit());
    teacher.setInvoice(request.getInvoice());

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

    public TeacherResponse saveTeacherWithImages(TeacherRequests request, int masterAdminId,
                                                MultipartFile addharImage,
                                                MultipartFile markshitImage) {
        // Validate uniqueness and load master admin
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
        teacher.setBranchName(request.getBranchName());
        teacher.setEducation(request.getEducation());
        teacher.setMarkshit(request.getMarkshit());
        teacher.setInvoice(request.getInvoice());

        // Upload images if provided
        try {
            if (addharImage != null && !addharImage.isEmpty()) {
                Map<String, Object> uploadRes = cloudinaryService.upload(addharImage);
                Object url = uploadRes.get("secure_url");
                if (url != null) {
                    teacher.setAddharImage(url.toString());
                }
            }
            if (markshitImage != null && !markshitImage.isEmpty()) {
                Map<String, Object> uploadRes = cloudinaryService.upload(markshitImage);
                Object url = uploadRes.get("secure_url");
                if (url != null) {
                    teacher.setMarkshitImage(url.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload images", e);
        }

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
        
        // Update new fields
        teacher.setBranchName(request.getBranchName());
        teacher.setEducation(request.getEducation());
        teacher.setMarkshit(request.getMarkshit());
        teacher.setInvoice(request.getInvoice());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponse(updatedTeacher);
    }

    public void deleteTeacher(int id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
        if (studentRepo.existsByTeacher(teacher)) {
            throw new IllegalStateException("Cannot delete teacher with linked students");
        }
        
        // Attempt to delete images from Cloudinary if present
        try {
            if (teacher.getAddharImage() != null && !teacher.getAddharImage().isEmpty()) {
                String publicId = extractCloudinaryPublicId(teacher.getAddharImage());
                if (publicId != null) {
                    cloudinaryService.delete(publicId);
                }
            }
            if (teacher.getMarkshitImage() != null && !teacher.getMarkshitImage().isEmpty()) {
                String publicId = extractCloudinaryPublicId(teacher.getMarkshitImage());
                if (publicId != null) {
                    cloudinaryService.delete(publicId);
                }
            }
        } catch (IOException e) {
            // Log and proceed with deletion to avoid orphan DB records
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
        response.setRole(teacher.getRole());
        response.setBranchNames(teacher.getBranchName());
        
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

    private String extractCloudinaryPublicId(String url) {
        // Typical Cloudinary URL: https://res.cloudinary.com/<cloud>/image/upload/v<version>/<public_id>.<ext>
        // Capture <public_id> possibly with folders
        Pattern pattern = Pattern.compile("/upload/(?:v\\d+/)?([^.#?]+)\\.[a-zA-Z0-9]+(?:[?#].*)?$");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


}