package com.exalt.bank_account_test.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public class AccountRepositoryAdapter implements AccountRepository {

    @Autowired
    private JpaAccountRepository jpaAccountRepository;

    public AccountEntity save(AccountEntity account) {
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
