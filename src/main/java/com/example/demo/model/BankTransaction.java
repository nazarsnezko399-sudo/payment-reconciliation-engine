package com.example.demo.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_transactions")

public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Tytuł przelewu jest niezbędny do rozliczenia
    private String transactionTitle;

    @Column(nullable = false) // Kwota nie może być pusta
    private BigDecimal amount;

    @Column(nullable = false) // data księgowania nie może być pusta
    private LocalDateTime bookingDate;

    @ManyToOne
    @JoinColumn (name = "order_id")
    private Order order;
}
