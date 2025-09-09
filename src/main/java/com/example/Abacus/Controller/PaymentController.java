package com.example.Abacus.Controller;

import com.example.Abacus.Model.Payment;
import com.example.Abacus.Repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepo paymentRepository;

    @GetMapping("/{receiptNo}")
    public ResponseEntity<String> getReceiptDetails(@PathVariable String receiptNo) {
        Payment payment = paymentRepository.findByReceiptNo(receiptNo)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));

        String htmlResponse = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1'>" +
                "<style>" +
                "body { margin:0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background:#f8f9fc; color:#333; }" +
                ".header { background: linear-gradient(90deg, #007bff, #00c6ff); color:white; padding:20px; text-align:center; }" +
                ".header h1 { margin:0; font-size:32px; letter-spacing:1px; }" +
                ".header h2 { margin:5px 0 0; font-weight:normal; font-size:20px; }" +
                ".container { width:100%; max-width:1000px; margin:20px auto; background:white; padding:40px; " +
                "box-shadow:0 4px 20px rgba(0,0,0,0.1); border-radius:10px; }" +
                ".row { display:flex; justify-content:space-between; padding:12px 0; border-bottom:1px solid #eee; }" +
                ".row:last-child { border-bottom:none; }" +
                ".label { font-weight:bold; color:#555; font-size:16px; }" +
                ".value { font-size:16px; color:#000; }" +
                ".footer { text-align:center; margin-top:40px; font-size:15px; color:#777; font-style:italic; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<h1>ABACUS INSTITUTE</h1>" +
                "<h2>Payment Receipt</h2>" +
                "</div>" +
                "<div class='container'>" +
                "<div class='row'><span class='label'>Receipt No:</span><span class='value'>" + payment.getReceiptNo() + "</span></div>" +
                "<div class='row'><span class='label'>Teacher:</span><span class='value'>" + payment.getTeacher().getFirstName() + " " + payment.getTeacher().getLastName() + "</span></div>" +
                "<div class='row'><span class='label'>Email:</span><span class='value'>" + payment.getTeacher().getEmail() + "</span></div>" +
                "<div class='row'><span class='label'>Total Fees:</span><span class='value'>₹" + payment.getFees() + "</span></div>" +
                "<div class='row'><span class='label'>Paid:</span><span class='value'>₹" + payment.getPaid() + "</span></div>" +
                "<div class='row'><span class='label'>Remaining:</span><span class='value'>₹" + payment.getRemainingAmount() + "</span></div>" +
                "<div class='row'><span class='label'>Payment Mode:</span><span class='value'>" + payment.getPaymentMode() + "</span></div>" +
                "<p class='footer'>Thank you for your payment!</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlResponse);
    }
}
