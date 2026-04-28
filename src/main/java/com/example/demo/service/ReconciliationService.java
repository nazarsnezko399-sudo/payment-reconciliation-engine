package com.example.demo.service;

import com.example.demo.model.BankTransaction;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.BankTransactionRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ReconciliationService {

    // Zależności
    private final OrderRepository orderRepository;
    private final BankTransactionRepository transactionRepository;
    private final FinancialValidator financialValidator;

    public ReconciliationService(OrderRepository orderRepository, BankTransactionRepository transactionRepository, FinancialValidator financialValidator) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.financialValidator = financialValidator;
    }



    // Walidator zamówien
    public void validateOrderForPayment(Order order){
        if (order == null){ // ten if jest opcjonalny ma za funkcje tylko dodatkowego zabezpieczenia przed nie istniejacymi zamówieniami
            throw new IllegalArgumentException("Error: Attempt to assign payment to a non-existent order.");
        }

        if (order.getStatus() == OrderStatus.PAID){
            throw new IllegalStateException("Error: Order has already been paid");
        }

        if (order.getStatus() == OrderStatus.CANCELLED){
            throw new IllegalStateException("Error: Order has been cancelled");
        }
    }

    // Algorytm księgowania wpłat
    public void processPayment(Long orderId, BankTransaction transaction){

        // 1. walidacja kwoty czy nie jest ujemna
        financialValidator.validateAmount(transaction.getAmount());

        // 2. Szukaninie zamówienia w bazie danych
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Error: Order with id " + orderId + " does not exist"));

        // 3. Sprawdzamy status zamówienia
        validateOrderForPayment(order);

        // 4. Jeśli wszystkie poprzednie kroki zostały wykonane przypisujemy zamówienie do transakcji
        transaction.setOrder(order);

        // 5. Zapisujemy transakcje do bazy
        transactionRepository.save(transaction);

    }

}
