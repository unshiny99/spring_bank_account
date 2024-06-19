package com.exalt.bank_account_test.domain.service;

import java.util.List;
import java.util.UUID;

import com.exalt.bank_account_test.domain.model.Transaction;

public interface AccountService {
    UUID createAccount();
    String consultBalance(UUID id);
    List<Transaction> consultTransactionHistory(UUID id);
    boolean depositMoney(UUID id, Transaction transaction);
    boolean withdrawMoney(UUID id, Transaction transaction);
}
