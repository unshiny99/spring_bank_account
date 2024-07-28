package com.exalt.bank_account_test.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public class AccountRepositoryAdapter implements AccountRepository {

    private JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public AccountEntity saveAccount(AccountEntity account) {
        return jpaAccountRepository.save(account);
    }

    @Override
    public Optional<AccountEntity> findById(UUID id) {
        return jpaAccountRepository.findById(id);
    }

    @Override
    public List<AccountEntity> findAll() {
        return jpaAccountRepository.findAll();
    }
}
