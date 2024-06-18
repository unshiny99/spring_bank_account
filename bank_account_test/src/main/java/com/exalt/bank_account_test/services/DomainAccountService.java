package com.exalt.bank_account_test.services;

import java.util.UUID;

import com.exalt.bank_account_test.domain.Account;
import com.exalt.bank_account_test.domain.Transaction;
import com.exalt.bank_account_test.repositories.AccountRepository;

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
    public void consultBalance(UUID id) {
        Account account = getAccount(id);
        double balance = account.getBalance();
        System.out.println("Solde : " + balance);
    }

    @Override
    public void consultTransactionHistory(UUID id) {
        Account account = getAccount(id);
        account.consultTransactionHistory();
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
