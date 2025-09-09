package com.example.Abacus.Controller;

import com.example.Abacus.Model.Payment;
import com.example.Abacus.Repo.PaymentRepo;
import com.example.Abacus.Service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;

@RestController
// @RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private PaymentRepo paymentRepository;

    @GetMapping("/{paymentId}/download")
    public ResponseEntity<ByteArrayResource> downloadReceipt(@PathVariable int paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + paymentId));

        // Generate the PDF file
        String filePath = receiptService.generateReceipt(payment);

        try {
            File file = new File(filePath);
            byte[] pdfBytes = Files.readAllBytes(file.toPath());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(new ByteArrayResource(pdfBytes));

        } catch (Exception e) {
            throw new RuntimeException("Error while downloading PDF", e);
        }
    }
}
