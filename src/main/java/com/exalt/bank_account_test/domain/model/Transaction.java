package com.exalt.bank_account_test.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private double amount;
    // we consider that sometimes operations are not made directly, so we store the transaction "request" date
    private LocalDateTime dateTime;
    private Account account;

    public Transaction() {
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", amount=" + amount + ", dateTime=" + dateTime + ", account=" + account + "]";
    }
}
