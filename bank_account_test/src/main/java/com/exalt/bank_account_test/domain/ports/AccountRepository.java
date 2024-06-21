package com.exalt.bank_account_test.domain.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public interface AccountRepository {
    AccountEntity save(AccountEntity account);

    Optional<AccountEntity> findById(UUID id);
    List<AccountEntity> findAll();
}
