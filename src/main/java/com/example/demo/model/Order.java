package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "Orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber; //identyfikator dla kienta, np. "ORD-001"

    @Column(nullable = false)
    private BigDecimal price; // Kwota wpłaty od klienta

    private String status;

}


