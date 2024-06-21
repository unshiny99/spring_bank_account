package com.exalt.bank_account_test.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public class Account {
    private UUID id;
    // to simplify, we consider that we use only one currency here (euros for example)
    private double balance;
    // list that will contain our transactions history
    private List<Transaction> transactions;

    public Account() {
        this.id = UUID.randomUUID();
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public Account(double amount) {
        this.id = UUID.randomUUID();
        this.balance = amount;
        this.transactions = new ArrayList<>();
    }

    /**
     * to deposit money in the account
     * @param transaction amount and date
     * @return boolean indicating the success state
     */
    public boolean depositMoney(Transaction transaction) {
        // check that we pass a strictly positive value in the transaction object in that case
        if(transaction.getAmount() < 0) {
            return false;
        }
        
        this.balance += transaction.getAmount();
        this.transactions.add(transaction);
        return true;
    }

    /**
     * to withdraw money in the account
     * @param transaction amount and date
     * @return boolean indicating the success state
     */
    public boolean withdrawMoney(Transaction transaction) {
        // check that we pass a strictly negative value in the transaction object in that case
        if(transaction.getAmount() > 0) {
            return false;
        }

        this.balance += transaction.getAmount();
        this.transactions.add(transaction);
        return true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * getter to be able to consult the balance
     * @return the amount of money in euros
     */
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * getter to retrieve the transaction history
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * method to display the transaction history
     */
    public void consultTransactionHistory() {
        System.out.println("Historique des transactions :");
        for(Transaction transaction : transactions) {
            System.out.println(transaction.toString());
        }
    }

    // convert entity to domain model
    public AccountEntity toEntity() {
        AccountEntity account = new AccountEntity();
        account.setId(this.id);
        account.setBalance(this.balance);
        account.setTransactions(transactions);
        return account;
    }

    @Override
    public String toString() {
        return "Account [balance=" + balance + ", transactions=" + transactions + "]";
    }
}
