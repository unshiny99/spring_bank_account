package com.exalt.bank_account_test.domain;

import java.time.Instant;
import java.util.Date;

public class Transaction {
    private double amount;
    // we consider that sometimes operations are not made directly, so we store the transaction "request" date
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

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", date=" + date + "]";
    }
}
