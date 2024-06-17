package com.exalt.bank_account_test.domain;

import java.util.ArrayList;
import java.util.List;

public class Account {
    // to simplify, we consider that we use only one currency here (euros for example)
    private double amount;
    // list that will contain our transactions history
    private List<Transaction> transactions;

    public Account() {
        this.amount = 0.0;
        this.transactions = new ArrayList<>();
    }

    public Account(double amount) {
        this.amount = amount;
        this.transactions = new ArrayList<>();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account [amount=" + amount + ", transactions=" + transactions + "]";
    }
}
