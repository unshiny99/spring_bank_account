package com.exalt.bank_account_test.services;

import java.util.UUID;

import com.exalt.bank_account_test.domain.Transaction;

public interface AccountService {
    void createAccount();
    void consultBalance(UUID id);
    void consultTransactionHistory(UUID id);
    boolean depositMoney(UUID id, Transaction transaction);
    boolean withdrawMoney(UUID id, Transaction transaction);
}
