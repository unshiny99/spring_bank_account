package com.exalt.bank_account_test.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

@Service
public interface AccountService {
    UUID createAccount(AccountEntity accountEntity);
    List<AccountEntity> getAccounts();
    double consultBalance(UUID id);
    List<Transaction> consultTransactionHistory(UUID id);
    boolean depositMoney(UUID id, Transaction transaction);
    boolean withdrawMoney(UUID id, Transaction transaction);
}
