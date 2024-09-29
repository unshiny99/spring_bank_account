package com.exalt.bank_account_test.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;

@Service
public interface AccountService {
    UUID createAccount(Account account);
    List<Account> getAccounts();
    double consultBalance(UUID id);
    List<Transaction> consultTransactionHistory(UUID id);
    List<Transaction> consultAllTransactions();
    boolean depositMoney(UUID id, Transaction transaction);
    boolean withdrawMoney(UUID id, Transaction transaction);
}
