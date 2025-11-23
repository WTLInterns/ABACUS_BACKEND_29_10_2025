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

import java.math.BigDecimal;
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
    teacher.setTeacherId(request.getTeacherId());
    
    
    // Handle fees calculation
    Long fees = request.getFees(); // Will be null if not provided
    Long paid = request.getPaid(); // Will be null if not provided
    
    // If fees is not provided, set default values
    if (fees == null) {
        fees = 15000L; // Default to 15000 if not provided
    }
    
    // If paid is not provided, default to 0
    if (paid == null) {
        paid = 0L;
    }
    
    Long remaining = fees - paid;
    
    teacher.setFees(fees);
    teacher.setRemainingAmount(remaining);
    
    teacher.setMasterAdmin(masterAdmin);
    teacher.setBranchName(request.getBranchName());
    
    // Set new fields from request
    teacher.setEducation(request.getEducation());
    teacher.setMarkshit(request.getMarkshit());
    teacher.setInvoice(request.getInvoice());
    teacher.setProfilePicture(request.getProfilePicture());

    Teacher savedTeacher = teacherRepository.save(teacher);

    if (paid > 0) {
        Payment payment = new Payment();
        payment.setReceiptNo("REC" + System.currentTimeMillis());
        payment.setPaymentMode(request.getPaymentType());
        payment.setFees(fees);
        payment.setPaid(paid);
        payment.setRemainingAmount(remaining);
        payment.setTeacher(savedTeacher);           

        paymentRepo.save(payment);

        receiptService.generateReceipt(payment);
    }

    return mapToResponse(savedTeacher);
}

    public TeacherResponse saveTeacherWithImages(TeacherRequests request, int masterAdminId,
                                                MultipartFile addharImage,
                                                MultipartFile markshitImage,
                                                MultipartFile profilePicture) {
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
        teacher.setTeacherId(request.getTeacherId());
        teacher.setRole("TEACHER");
        
        // Handle fees calculation
        Long fees = request.getFees(); // Will be null if not provided
        Long paid = request.getPaid(); // Will be null if not provided
        
        // If fees is not provided, set default values
        if (fees == null) {
            fees = 15000L; // Default to 15000 if not provided
        }
        
        // If paid is not provided, default to 0
        if (paid == null) {
            paid = 0L;
        }
        
        Long remaining = fees - paid;
        
        teacher.setFees(fees);
        teacher.setRemainingAmount(remaining);
        
        teacher.setMasterAdmin(masterAdmin);
        teacher.setBranchName(request.getBranchName());
        teacher.setEducation(request.getEducation());
        teacher.setMarkshit(request.getMarkshit());
        teacher.setInvoice(request.getInvoice());
        teacher.setTeacherId(request.getTeacherId());

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
            // Handle profile picture upload
            if (profilePicture != null && !profilePicture.isEmpty()) {
                Map<String, Object> uploadRes = cloudinaryService.upload(profilePicture);
                Object url = uploadRes.get("secure_url");
                if (url != null) {
                    teacher.setProfilePicture(url.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload images", e);
        }

        Teacher savedTeacher = teacherRepository.save(teacher);

        if (paid > 0) {
            Payment payment = new Payment();
            payment.setReceiptNo("REC" + System.currentTimeMillis());
            payment.setPaymentMode(request.getPaymentType());
            payment.setFees(fees);
            payment.setPaid(paid);
            payment.setRemainingAmount(remaining);
            payment.setTeacher(savedTeacher);

            paymentRepo.save(payment);

            receiptService.generateReceipt(payment);
        }

        return mapToResponse(savedTeacher);
    }

    public TeacherResponse updateTeacherWithImages(int id, TeacherRequests request,
                                                MultipartFile addharImage,
                                                MultipartFile markshitImage,
                                                MultipartFile profilePicture) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));

        // Store old image URLs for cleanup
        String oldAddharImageUrl = teacher.getAddharImage();
        String oldMarkshitImageUrl = teacher.getMarkshitImage();
        String oldProfilePictureUrl = teacher.getProfilePicture();

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(request.getPassword());
        teacher.setTeacherId(request.getTeacherId());
        
        // Update new fields
        teacher.setBranchName(request.getBranchName());
        teacher.setEducation(request.getEducation());
        teacher.setMarkshit(request.getMarkshit());
        teacher.setInvoice(request.getInvoice());
        
        // Handle fees update if provided
        if (request.getFees() != null) {
            teacher.setFees(request.getFees());
        }
        
        // Handle payment if provided
        if (request.getPaid() != null && request.getPaid() > 0) {
            // Add the new payment to existing payments
            Long currentRemaining = teacher.getRemainingAmount() != null ? teacher.getRemainingAmount() : teacher.getFees();
            Long newRemaining = currentRemaining - request.getPaid();
            
            teacher.setRemainingAmount(newRemaining);
            
            // Create a new payment record
            Payment payment = new Payment();
            payment.setReceiptNo("REC" + System.currentTimeMillis());
            payment.setPaymentMode(request.getPaymentType());
            payment.setFees(teacher.getFees());
            payment.setPaid(request.getPaid());
            payment.setRemainingAmount(newRemaining);
            payment.setTeacher(teacher);
            
            paymentRepo.save(payment);
            
            receiptService.generateReceipt(payment);
        }

        // Upload new images if provided and delete old ones
        try {
            // Handle aadhar image
            if (addharImage != null && !addharImage.isEmpty()) {
                // Delete old image from Cloudinary if it exists
                if (oldAddharImageUrl != null && !oldAddharImageUrl.isEmpty()) {
                    try {
                        String publicId = extractCloudinaryPublicId(oldAddharImageUrl);
                        if (publicId != null) {
                            cloudinaryService.delete(publicId);
                        }
                    } catch (IOException e) {
                        // Log error but continue with upload
                        System.err.println("Failed to delete old aadhar image: " + e.getMessage());
                    }
                }
                
                // Upload new image
                Map<String, Object> uploadRes = cloudinaryService.upload(addharImage);
                Object url = uploadRes.get("secure_url");
                if (url != null) {
                    teacher.setAddharImage(url.toString());
                }
            }
            
            // Handle marksheet image
            if (markshitImage != null && !markshitImage.isEmpty()) {
                // Delete old image from Cloudinary if it exists
                if (oldMarkshitImageUrl != null && !oldMarkshitImageUrl.isEmpty()) {
                    try {
                        String publicId = extractCloudinaryPublicId(oldMarkshitImageUrl);
                        if (publicId != null) {
                            cloudinaryService.delete(publicId);
                        }
                    } catch (IOException e) {
                        // Log error but continue with upload
                        System.err.println("Failed to delete old marksheet image: " + e.getMessage());
                    }
                }
                
                // Upload new image
                Map<String, Object> uploadRes = cloudinaryService.upload(markshitImage);
                Object url = uploadRes.get("secure_url");
                if (url != null) {
                    teacher.setMarkshitImage(url.toString());
                }
            }
            
            // Handle profile picture
            if (profilePicture != null && !profilePicture.isEmpty()) {
                // Delete old image from Cloudinary if it exists
                if (oldProfilePictureUrl != null && !oldProfilePictureUrl.isEmpty()) {
                    try {
                        String publicId = extractCloudinaryPublicId(oldProfilePictureUrl);
                        if (publicId != null) {
                            cloudinaryService.delete(publicId);
                        }
                    } catch (IOException e) {
                        // Log error but continue with upload
                        System.err.println("Failed to delete old profile picture: " + e.getMessage());
                    }
                }
                
                // Upload new image
                Map<String, Object> uploadRes = cloudinaryService.upload(profilePicture);
                Object url = uploadRes.get("secure_url");
                if (url != null) {
                    teacher.setProfilePicture(url.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload images", e);
        }

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponse(updatedTeacher);
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
        teacher.setTeacherId(request.getTeacherId());
        
        // Update new fields
        teacher.setBranchName(request.getBranchName());
        teacher.setEducation(request.getEducation());
        teacher.setMarkshit(request.getMarkshit());
        teacher.setInvoice(request.getInvoice());
        teacher.setProfilePicture(request.getProfilePicture());
        
        // Handle fees update if provided
        if (request.getFees() != null) {
            teacher.setFees(request.getFees());
        }
        
        // Handle payment if provided
        if (request.getPaid() != null && request.getPaid() > 0) {
            // Add the new payment to existing payments
            Long currentRemaining = teacher.getRemainingAmount() != null ? teacher.getRemainingAmount() : teacher.getFees();
            Long newRemaining = currentRemaining - request.getPaid();
            
            teacher.setRemainingAmount(newRemaining);
            
            // Create a new payment record
            Payment payment = new Payment();
            payment.setReceiptNo("REC" + System.currentTimeMillis());
            payment.setPaymentMode(request.getPaymentType());
            payment.setFees(teacher.getFees());
            payment.setPaid(request.getPaid());
            payment.setRemainingAmount(newRemaining);
            payment.setTeacher(teacher);
            
            paymentRepo.save(payment);
            
            receiptService.generateReceipt(payment);
        }

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
            // Delete profile picture from Cloudinary if present
            if (teacher.getProfilePicture() != null && !teacher.getProfilePicture().isEmpty()) {
                String publicId = extractCloudinaryPublicId(teacher.getProfilePicture());
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
        response.setProfilePicture(teacher.getProfilePicture()); 
        response.setAadharImage(teacher.getAddharImage());
        response.setTeacherId(teacher.getTeacherId());
        response.setMarkshitImage(teacher.getMarkshitImage());
        
        // Add fees information
        response.setFees(teacher.getFees());
        response.setRemainingAmount(teacher.getRemainingAmount());
        
        // Add additional fields
        response.setEducation(teacher.getEducation());
        response.setMarkshit(teacher.getMarkshit());
        response.setInvoice(teacher.getInvoice());
        
        // Set payment type from last payment if available
        if (teacher.getPayments() != null && !teacher.getPayments().isEmpty() && !teacher.getPayments().isEmpty()) {
            Payment lastPayment = teacher.getPayments().get(teacher.getPayments().size() - 1);
            response.setPaymentType(lastPayment.getPaymentMode());
        }
        
        // Calculate total paid from all payments
        if (teacher.getPayments() != null && !teacher.getPayments().isEmpty()) {
            Long totalPaid = teacher.getPayments().stream()
                .mapToLong(Payment::getPaid)
                .sum();
            response.setPaid(totalPaid);
        }
        
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

    // Add clear payment method
    public Teacher clearTeacherPayment(int id, String amountPaid) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
        
        // Validate the amountPaid
        if (amountPaid == null || amountPaid.isEmpty()) {
            throw new IllegalArgumentException("Amount paid cannot be null or empty");
        }
        
        try {
            // Get current ledger paid amount (could be null)
            String currentLedgerPaidAmount = teacher.getLedgerPaidAmount();
            if (currentLedgerPaidAmount == null) {
                currentLedgerPaidAmount = "0";
            }
            
            // Convert both values to BigDecimal for precise arithmetic
            BigDecimal currentAmount = new BigDecimal(currentLedgerPaidAmount);
            BigDecimal newAmount = new BigDecimal(amountPaid);
            
            // Add the amounts together
            BigDecimal totalAmount = currentAmount.add(newAmount);
            
            // Update the ledgerPaidAmount field
            teacher.setLedgerPaidAmount(totalAmount.toString());
            
            // Save and return the updated teacher
            return teacherRepository.save(teacher);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear teacher payment: " + e.getMessage());
        }
    }

}