package com.exalt.bank_account_test.infrastructure.persistence.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.exalt.bank_account_test.domain.model.Transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double amount;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    public TransactionEntity() {
    }

    public TransactionEntity(Transaction transaction, AccountEntity accountEntity) {
        this.amount = transaction.getAmount();
        this.dateTime = transaction.getDateTime();
        this.account = accountEntity;
    }

    // convert entity to domain model
    public Transaction toDomain() {
        Transaction transaction = new Transaction();
        transaction.setId(this.id);
        transaction.setAmount(this.amount);
        transaction.setDateTime(this.dateTime);
        return transaction;
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

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "TransactionEntity [id=" + id + ", amount=" + amount + ", dateTime=" + dateTime + ", account=" + account
                + "]";
    }
}
