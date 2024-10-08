package com.exalt.bank_account_test.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exalt.bank_account_test.adapters.out.persistence.AccountRepositoryAdapter;
import com.exalt.bank_account_test.adapters.out.persistence.TransactionRepositoryAdapter;
import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

@Service
public class DomainAccountService implements AccountService {
    private final AccountRepositoryAdapter accountRepositoryAdapter;
    private final TransactionRepositoryAdapter transactionRepositoryAdapter;

    public DomainAccountService(AccountRepositoryAdapter accountRepositoryAdapter, TransactionRepositoryAdapter transactionRepositoryAdapter) {
        this.accountRepositoryAdapter = accountRepositoryAdapter;
        this.transactionRepositoryAdapter = transactionRepositoryAdapter;
    }

    @Override
    public UUID createAccount(Account account) {
        AccountEntity generatedEntity = accountRepositoryAdapter.saveAccount(account);
        return generatedEntity.getId();
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepositoryAdapter.findAll();
    }

    @Override
    public double consultBalance(UUID id) {
        Account account = getAccount(id);
        double balance = account.getBalance();

        return balance;
    }

    @Override
    public List<Transaction> consultTransactionHistory(UUID id) {    
        Account account = getAccount(id);
        return account.getTransactions();
    }

    @Override
    public List<Transaction> consultAllTransactions() {
        return transactionRepositoryAdapter.findAll();
    }

    @Override
    public boolean depositMoney(UUID id, Transaction transaction) {
        Account account = getAccount(id);
        boolean success = account.depositMoney(transaction);

        if(success) {
            accountRepositoryAdapter.saveAccount(account);
        }
        return success;
    }

    @Override
    public boolean withdrawMoney(UUID id, Transaction transaction) {
        Account account = getAccount(id);
        boolean success = account.withdrawMoney(transaction);

        if(success) {
            accountRepositoryAdapter.saveAccount(account);
        }
        return success;
    }

    /**
     * get the matching acccount for a given ID,
     * @param id
     * @return Account or RuntimeException if not found
     */
     private Account getAccount(UUID id) {
        return accountRepositoryAdapter
          .findById(id)
          .map(AccountEntity::toDomain)
          .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
