package com.exalt.bank_account_test.infrastructure.persistence.entities;

import java.util.List;
import java.util.UUID;

import com.exalt.bank_account_test.domain.model.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double balance;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    public AccountEntity() {}

    // constructor to create entity from domain model
    public AccountEntity(Account account) {
        this.balance = account.getBalance();
    }

    // convert entity to domain model
    public Account toDomain() {
        Account account = new Account();
        account.setId(this.id);
        account.setBalance(this.balance);
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

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "AccountEntity [id=" + id + ", balance=" + balance + ", transactions=" + transactions + "]";
    }
}
