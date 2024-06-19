package com.exalt.bank_account_test.domain.service;

import java.util.List;
import java.util.UUID;

import com.exalt.bank_account_test.adapters.repository.AccountRepository;
import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;

public class DomainAccountService implements AccountService {
    private final AccountRepository accountRepository;

    public DomainAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createAccount() {
        Account account = new Account();
        accountRepository.save(account);

        return account.getId();
    }

    @Override
    public String consultBalance(UUID id) {
        Account account = getAccount(id);
        double balance = account.getBalance();
        System.out.println("Solde : " + balance);

        return String.valueOf(balance) + " €";
    }

    @Override
    public List<Transaction> consultTransactionHistory(UUID id) {
        Account account = getAccount(id);

        return account.getTransactions();
    }

    @Override
    public boolean depositMoney(UUID id, Transaction transaction) {
        Account account = getAccount(id);
        boolean result = account.depositMoney(transaction);

        if(result == true) {
            accountRepository.save(account);
        }
        return result;
    }

    @Override
    public boolean withdrawMoney(UUID id, Transaction transaction) {
        Account account = getAccount(id);
        boolean result = account.withdrawMoney(transaction);

        if(result == true) {
            accountRepository.save(account);
        }
        return result;
    }

    /**
     * get the matching acccount for a given ID
     * @param id
     * @return Account
     */
     private Account getAccount(UUID id) {
        return accountRepository
          .findById(id)
          .orElseThrow(RuntimeException::new);
    }
}
