package com.example.demo.service;

import com.example.demo.model.BankTransaction;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.BankTransactionRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReconciliationService {

    // Zależności
    private final OrderRepository orderRepository;
    private final BankTransactionRepository transactionRepository;
    private final FinancialValidator financialValidator;
    private final ReferenceMatcher referenceMatcher;

    public ReconciliationService(OrderRepository orderRepository, BankTransactionRepository transactionRepository, FinancialValidator financialValidator, ReferenceMatcher referenceMatcher) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.financialValidator = financialValidator;
        this.referenceMatcher = referenceMatcher;
    }

    public Order getOrderDetails(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->new IllegalArgumentException("Error: Order with id " + orderId + " not found"));
    }

    // Walidator płatności za zamówienie
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
    @Transactional
    public String processPayment(BankTransaction transaction){


        // 1 Walidacja kwoty (Twoje zabezpieczenie - np. czy nie jest ujemna)
        financialValidator.validateAmount(transaction.getAmount());

        // 2 Wyciągamy tytuł przelewu i szukamy w nim numeru zamówienia za pomocą Regex
        String transactionTitle = transaction.getTransactionTitle();
        String orderNumber = referenceMatcher.extractOrderNumber(transactionTitle)
                .orElseThrow(() -> new IllegalArgumentException("Error: Cannot find order number in title: " + transactionTitle));

        // 3 Szukanie zamówienia w bazie po wyciągniętym numerze ORD... (zamiast po ID)
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Error: Order with number " + orderNumber + " does not exist"));

        // 4 Sprawdzamy status zamówienia
        validateOrderForPayment(order);

        // 5 Przypisujemy zamówienie do transakcji (Klucz obcy dla bazy danych)
        transaction.setOrder(order);

        // 6 Zapisujemy transakcję do bazy (Zostawiamy ślad audytowy)
        transactionRepository.save(transaction);

        // 7 Aktualizujemy status zamówienia na opłacone
        order.setStatus(OrderStatus.PAID);

        return  orderNumber;
    }

}
