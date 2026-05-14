package com.example.demo.controller;


import com.example.demo.model.BankTransaction;
import com.example.demo.model.Order;
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
            @RequestBody BankTransaction transaction){

        String extractedOrderNumber = reconciliationService.processPayment(transaction);

        return ResponseEntity.ok("Successfully processed payment. Order ID: " + extractedOrderNumber);


    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        Order order = reconciliationService.getOrderDetails(id);
        return ResponseEntity.ok(order);
    }

}
