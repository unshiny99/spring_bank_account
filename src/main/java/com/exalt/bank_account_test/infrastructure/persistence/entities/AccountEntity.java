package com.exalt.bank_account_test.infrastructure.persistence.entities;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

    // we map by transaction ID
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions;

    public AccountEntity() {}

    // constructor to create entity from domain model
    public AccountEntity(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map((t) -> new TransactionEntity(t, this)).toList();
    }

    // convert entity to domain model
    public Account toDomain() {
        Account account = new Account();
        account.setId(this.id);
        account.setBalance(this.balance);

         // Convert each TransactionEntity to a Transaction and add to the account's transaction list
        List<Transaction> transactions = this.transactions.stream()
            .map(TransactionEntity::toDomain)
            .collect(Collectors.toList());

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
