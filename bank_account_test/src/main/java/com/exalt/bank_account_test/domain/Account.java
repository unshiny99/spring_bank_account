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
        
        this.amount += transaction.getAmount();
        this.transactions.add(transaction);
        return true;
    }

    /**
     * to withdraw money in the account
     * @param transaction amount and date
     * @return boolean indicating the success state
     */
    public boolean withdrawMoney(Transaction transaction) {
        // check that we pass a strictly positive value in the transaction object in that case
        if(transaction.getAmount() < 0) {
            return false;
        }

        this.amount -= transaction.getAmount();
        this.transactions.add(transaction);
        return true;
    }

    /**
     * getter to be able to consult the balance
     * @return the amount of money in euros
     */
    public double getAmount() {
        return amount;
    }

    /**
     * getter to retrieve the transaction history
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
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

    @Override
    public String toString() {
        return "Account [amount=" + amount + ", transactions=" + transactions + "]";
    }
}
