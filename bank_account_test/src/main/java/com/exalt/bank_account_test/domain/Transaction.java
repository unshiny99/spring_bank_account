package com.exalt.bank_account_test.domain;

import java.time.Instant;
import java.util.Date;

public class Transaction {
    private double amount;
    private Date date;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = Date.from(Instant.now());
    }

    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", date=" + date + "]";
    }
}
