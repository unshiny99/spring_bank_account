package com.exalt.bank_account_test.infrastructure.persistence.entities;

import java.util.List;
import java.util.UUID;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double balance;

    @ElementCollection
    @CollectionTable(name = "account_transactions", joinColumns = @JoinColumn(name = "account_id"))
    private List<Transaction> transactions;

    public AccountEntity() {
        this(new Account());
    }

    // constructor to create entity from domain model
    public AccountEntity(Account account) {
        this.balance = account.getBalance();
        this.transactions = account.getTransactions();
    }

    // convert entity to domain model
    public Account toDomain() {
        Account account = new Account();
        account.setId(this.id);
        account.setBalance(this.balance);
        account.setTransactions(transactions);
        return account;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "AccountEntity [id=" + id + ", balance=" + balance + ", transactions=" + transactions + "]";
    }
}
