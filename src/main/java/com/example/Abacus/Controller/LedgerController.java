package com.example.Abacus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Abacus.Model.Ledger;
import com.example.Abacus.Service.LedgerService;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    // Create a new ledger entry with payment screenshot
    @PostMapping("/create/{teacherId}/{inventoryId}")
    public ResponseEntity<Ledger> createLedger(
            @RequestBody Ledger ledger,
            @PathVariable int teacherId,
            @PathVariable int inventoryId,
            @RequestParam(value = "paymentScreenshot", required = false) MultipartFile paymentScreenshot) {
        Ledger createdLedger = ledgerService.createLedger(ledger, teacherId, inventoryId, paymentScreenshot);
        return ResponseEntity.ok(createdLedger);
    }

    // Get all ledger entries
    @GetMapping("/getAll")
    public ResponseEntity<List<Ledger>> getAllLedgers() {
        List<Ledger> ledgers = ledgerService.getAllLedgers();
        return ResponseEntity.ok(ledgers);
    }

    // Get ledger entries by teacher ID
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Ledger>> getLedgersByTeacherId(@PathVariable int teacherId) {
        List<Ledger> ledgers = ledgerService.getLedgersByTeacherId(teacherId);
        return ResponseEntity.ok(ledgers);
    }

    // Get ledger entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<Ledger> getLedgerById(@PathVariable int id) {
        Ledger ledger = ledgerService.getLedgerById(id);
        return ResponseEntity.ok(ledger);
    }

    // Update ledger entry with payment screenshot
    @PutMapping("/update/{id}")
    public ResponseEntity<Ledger> updateLedger(
            @PathVariable int id,
            @RequestBody Ledger ledgerDetails,
            @RequestParam(value = "paymentScreenshot", required = false) MultipartFile paymentScreenshot) {
        Ledger updatedLedger = ledgerService.updateLedger(id, ledgerDetails, paymentScreenshot);
        return ResponseEntity.ok(updatedLedger);
    }

    // Delete ledger entry
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLedger(@PathVariable int id) {
        ledgerService.deleteLedger(id);
        return ResponseEntity.ok("Ledger entry deleted successfully with id: " + id);
    }
    
    // Add payment to existing ledger entry with screenshot
    @PostMapping("/add-payment/{id}")
    public ResponseEntity<Ledger> addPaymentToLedger(
            @PathVariable int id,
            @RequestParam String paidPrice,
            @RequestParam(value = "paymentScreenshot", required = false) MultipartFile paymentScreenshot) {
        Ledger updatedLedger = ledgerService.addPaymentToLedger(id, paidPrice, paymentScreenshot);
        return ResponseEntity.ok(updatedLedger);
    }

    @GetMapping("/outstanding-price/{teacherId}")
    public ResponseEntity<String> getOutStandPriceByTeacherId(@PathVariable int teacherId) {
        String outStandPrice = ledgerService.getOutStandPriceByTeacherId(teacherId);
        return ResponseEntity.ok(outStandPrice);
    }
}