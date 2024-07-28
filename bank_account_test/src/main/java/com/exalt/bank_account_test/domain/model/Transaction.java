package com.exalt.bank_account_test.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class Transaction {
    private double amount;
    // we consider that sometimes operations are not made directly, so we store the transaction "request" date
    private LocalDateTime dateTime;

    protected Transaction() {
        this.dateTime = LocalDateTime.now();
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public Transaction(double amount, LocalDateTime dateTime) {
        this.amount = amount;
        if(dateTime != null) {
            this.dateTime = dateTime;
        } else {
            this.dateTime = LocalDateTime.now();
        }
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", dateTime=" + dateTime + "]";
    }
}
