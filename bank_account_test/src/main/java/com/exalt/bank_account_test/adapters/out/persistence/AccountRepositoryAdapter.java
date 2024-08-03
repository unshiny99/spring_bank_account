package com.exalt.bank_account_test.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;
import com.exalt.bank_account_test.infrastructure.persistence.entities.TransactionEntity;

public class AccountRepositoryAdapter implements AccountRepository {

    private JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public AccountEntity saveAccount(Account account) {
        return jpaAccountRepository.save(toEntity(account));
    }

    @Override
    public Optional<AccountEntity> findById(UUID id) {
        Optional<AccountEntity> accountEntity = jpaAccountRepository.findById(id);
        
        return accountEntity;
    }

    @Override
    public List<Account> findAll() {
        return jpaAccountRepository.findAll()
            .stream()
            .map(accountEntity -> accountEntity.toDomain())
            .collect(Collectors.toList());
    }

    // convert domain model to entity
    public AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(account.getBalance());
        accountEntity.setTransactions(account.getTransactions().stream().map(t -> new TransactionEntity(t, accountEntity)).collect(Collectors.toList()));

        return accountEntity;
    }
}
