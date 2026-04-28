package com.example.demo.controller;


import com.example.demo.model.BankTransaction;
import com.example.demo.service.ReconciliationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;

    // Zależność połaczenia z algorytmem księgowym
    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(
            @RequestParam Long orderId,
            @RequestBody BankTransaction transaction){

        reconciliationService.processPayment(orderId, transaction);

        return ResponseEntity.ok("Successfully processed payment. Order ID: " + orderId);


    }

}
