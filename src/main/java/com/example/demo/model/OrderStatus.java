package com.example.demo.model;

public enum OrderStatus {
    NEW, //Nowe zamówienie czeka na płatność
    PAID, //Opłacone, nie można przypisać więcej płatności
    CANCELLED, //Zamówienie zostało anulowane równiez nie można przypisać więcej wpłat
}
