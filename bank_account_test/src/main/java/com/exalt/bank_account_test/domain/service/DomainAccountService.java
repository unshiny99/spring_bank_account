package com.exalt.bank_account_test.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

@Service
public class DomainAccountService implements AccountService {
    private final AccountRepository accountRepository;

    public DomainAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createAccount(AccountEntity accountEntity) {
        AccountEntity generatedEntity = accountRepository.save(accountEntity);
        return generatedEntity.getId();
    }

    @Override
    public List<AccountEntity> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public String consultBalance(UUID id) {
        Account account = getAccount(id);
        double balance = account.getBalance();

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
            accountRepository.save(account.toEntity());
        }
        return result;
    }

    @Override
    public boolean withdrawMoney(UUID id, Transaction transaction) {
        Account account = getAccount(id);
        boolean result = account.withdrawMoney(transaction);

        if(result == true) {
            accountRepository.save(account.toEntity());
        }
        return result;
    }

    /**
     * get the matching acccount for a given ID,
     * @param id
     * @return Account or RuntimeException if not found
     */
     private Account getAccount(UUID id) {
        return accountRepository
          .findById(id)
          .map(AccountEntity::toDomain)
          .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
