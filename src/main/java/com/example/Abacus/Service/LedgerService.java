package com.example.Abacus.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Abacus.Model.Inventory;
import com.example.Abacus.Model.Ledger;
import com.example.Abacus.Model.Teacher;
import com.example.Abacus.Repo.InventoryRepo;
import com.example.Abacus.Repo.LedgerRepo;
import com.example.Abacus.Repo.TeacherRepo;
import com.example.Abacus.exception.LedgerServiceException;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepo ledgerRepo;
    
    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private InventoryRepo inventoryRepo;
    
    @Autowired
    private CloudinaryService cloudinaryService;

    public Ledger createLedger(Ledger ledger, int teacherId, int inventoryId, MultipartFile paymentScreenshot) {
        Teacher teacher = teacherRepo.findById(teacherId)
            .orElseThrow(() -> new LedgerServiceException("Teacher not found with id: " + teacherId));
        
        Inventory inventory = this.inventoryRepo.findById(inventoryId)
            .orElseThrow(() -> new LedgerServiceException("Inventory not found with id: " + inventoryId));
        
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ledger.setDate(currentDate);
        
        ledger.setName(inventory.getItemName());
        
        String requestedQuantityStr = ledger.getQuantity();
        
        try {
            int requestedQuantity = Integer.parseInt(requestedQuantityStr);
            int availableQuantity = inventory.getQuantity();
            Long pricePerItem = inventory.getPricePerItem();
            
            if (requestedQuantity > availableQuantity) {
                String quantityErrorMessage = "Requested quantity (" + requestedQuantity + ") is greater than available quantity (" + availableQuantity + ") for item: " + inventory.getItemName();
                throw new LedgerServiceException(quantityErrorMessage);
            }
            
            Long totalPrice = (long) requestedQuantity * pricePerItem;
            ledger.setTotalPrice(totalPrice.toString());
            ledger.setPerPeicePrice(pricePerItem);
            
            String paidPriceStr = ledger.getPaidPrice();
            if (paidPriceStr != null && !paidPriceStr.isEmpty()) {
                Long paidPrice = Long.parseLong(paidPriceStr);
                if (paidPrice > totalPrice) {
                    String priceErrorMessage = "Paid price (" + paidPrice + ") cannot be greater than total price (" + totalPrice + ")";
                    throw new LedgerServiceException(priceErrorMessage);
                }
            }
            
            if (paymentScreenshot != null && !paymentScreenshot.isEmpty()) {
                try {
                    Map uploadResult = cloudinaryService.upload(paymentScreenshot);
                    ledger.setPaymentScreenshotUrl((String) uploadResult.get("secure_url"));
                    ledger.setPaymentScreenshotPublicId((String) uploadResult.get("public_id"));
                } catch (Exception e) {
                    System.err.println("Failed to upload payment screenshot to Cloudinary: " + e.getMessage());
                }
            }
            
            ledger.setTeacher(teacher);
            
            Ledger savedLedger = ledgerRepo.save(ledger);
            
            inventory.setQuantity(availableQuantity - requestedQuantity);
            inventoryRepo.save(inventory);
            
            return savedLedger;
            
        } catch (NumberFormatException e) {
            String formatErrorMessage = "Invalid quantity or price format: " + e.getMessage();
            throw new LedgerServiceException(formatErrorMessage);
        }
    }

    public List<Ledger> getAllLedgers() {
        return ledgerRepo.findAll();
    }

    public List<Ledger> getLedgersByTeacherId(int teacherId) {
        return ledgerRepo.findByTeacherId(teacherId);
    }

    public Ledger getLedgerById(int id) {
        return ledgerRepo.findById(id)
            .orElseThrow(() -> new LedgerServiceException("Ledger not found with id: " + id));
    }

    public Ledger updateLedger(int id, Ledger ledgerDetails, MultipartFile paymentScreenshot) {
        Ledger ledger = ledgerRepo.findById(id)
            .orElseThrow(() -> new LedgerServiceException("Ledger not found with id: " + id));
        
        String totalPriceStr = ledger.getTotalPrice();
        String paidPriceStr = ledgerDetails.getPaidPrice();
        
        try {
            if (totalPriceStr != null && paidPriceStr != null && !paidPriceStr.isEmpty()) {
                Long totalPrice = Long.parseLong(totalPriceStr);
                Long paidPrice = Long.parseLong(paidPriceStr);
                if (paidPrice > totalPrice) {
                    String priceErrorMessage = "Paid price (" + paidPrice + ") cannot be greater than total price (" + totalPrice + ")";
                    throw new LedgerServiceException(priceErrorMessage);
                }
            }
        } catch (NumberFormatException e) {
            String formatErrorMessage = "Invalid price format: " + e.getMessage();
            throw new LedgerServiceException(formatErrorMessage);
        }
        
        ledger.setName(ledgerDetails.getName());
        ledger.setQuantity(ledgerDetails.getQuantity());
        ledger.setTotalPrice(ledgerDetails.getTotalPrice());
        ledger.setDate(ledgerDetails.getDate());
        ledger.setPaidPrice(ledgerDetails.getPaidPrice());
        
        if (paymentScreenshot != null && !paymentScreenshot.isEmpty()) {
            try {
                if (ledger.getPaymentScreenshotPublicId() != null && !ledger.getPaymentScreenshotPublicId().isEmpty()) {
                    cloudinaryService.delete(ledger.getPaymentScreenshotPublicId());
                }
                
                Map uploadResult = cloudinaryService.upload(paymentScreenshot);
                ledger.setPaymentScreenshotUrl((String) uploadResult.get("secure_url"));
                ledger.setPaymentScreenshotPublicId((String) uploadResult.get("public_id"));
            } catch (Exception e) {
                System.err.println("Failed to upload payment screenshot to Cloudinary: " + e.getMessage());
            }
        }
        
        return ledgerRepo.save(ledger);
    }

    public void deleteLedger(int id) {
        Ledger ledger = ledgerRepo.findById(id)
            .orElseThrow(() -> new LedgerServiceException("Ledger not found with id: " + id));
        
        if (ledger.getPaymentScreenshotPublicId() != null && !ledger.getPaymentScreenshotPublicId().isEmpty()) {
            try {
                cloudinaryService.delete(ledger.getPaymentScreenshotPublicId());
            } catch (Exception e) {
                System.err.println("Failed to delete payment screenshot from Cloudinary: " + e.getMessage());
            }
        }
        
        ledgerRepo.delete(ledger);
    }
    
    public Ledger addPaymentToLedger(int id, String paidPrice, MultipartFile paymentScreenshot) {
        Ledger ledger = ledgerRepo.findById(id)
            .orElseThrow(() -> new LedgerServiceException("Ledger not found with id: " + id));
        
        try {
            String totalPriceStr = ledger.getTotalPrice();
            if (totalPriceStr != null && paidPrice != null && !paidPrice.isEmpty()) {
                Long totalPrice = Long.parseLong(totalPriceStr);
                Long paidPriceAmount = Long.parseLong(paidPrice);
                if (paidPriceAmount > totalPrice) {
                    String priceErrorMessage = "Paid price (" + paidPriceAmount + ") cannot be greater than total price (" + totalPrice + ")";
                    throw new LedgerServiceException(priceErrorMessage);
                }
            }
            
            ledger.setPaidPrice(paidPrice);
            
            if (paymentScreenshot != null && !paymentScreenshot.isEmpty()) {
                try {
                    if (ledger.getPaymentScreenshotPublicId() != null && !ledger.getPaymentScreenshotPublicId().isEmpty()) {
                        cloudinaryService.delete(ledger.getPaymentScreenshotPublicId());
                    }
                    
                    Map uploadResult = cloudinaryService.upload(paymentScreenshot);
                    ledger.setPaymentScreenshotUrl((String) uploadResult.get("secure_url"));
                    ledger.setPaymentScreenshotPublicId((String) uploadResult.get("public_id"));
                } catch (Exception e) {
                    System.err.println("Failed to upload payment screenshot to Cloudinary: " + e.getMessage());
                }
            }
            
            return ledgerRepo.save(ledger);
        } catch (NumberFormatException e) {
            String formatErrorMessage = "Invalid price format: " + e.getMessage();
            throw new LedgerServiceException(formatErrorMessage);
        }
    }


    public String getOutStandPriceByTeacherId(int teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId)
            .orElseThrow(() -> new LedgerServiceException("Teacher not found with id: " + teacherId));

        BigDecimal outstanding = teacher.getLedgers().stream()
            .filter(ledger -> {
                BigDecimal total = new BigDecimal(ledger.getTotalPrice());
                BigDecimal paid = new BigDecimal(ledger.getPaidPrice());
                return total.subtract(paid).compareTo(BigDecimal.ZERO) > 0;
            })
            .map(ledger -> new BigDecimal(ledger.getTotalPrice())
                .subtract(new BigDecimal(ledger.getPaidPrice())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return outstanding.toString();
    }


}