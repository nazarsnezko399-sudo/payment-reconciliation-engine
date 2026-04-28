package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class FinancialValidator {

    public void validateAmount(BigDecimal amount){
        if(amount==null){
            throw new IllegalArgumentException("Amount cannot be null"); // Wyrzuca komunikat ze kwota = null
        }

        if (amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Amount must be greater than zero: " + amount); //Wyrzuca Komunikat w przypadku gdzie kwota jest ujemna
        }
    }
}
