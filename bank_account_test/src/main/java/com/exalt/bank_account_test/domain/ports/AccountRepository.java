package com.exalt.bank_account_test.domain.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

@Repository
public interface AccountRepository {
    AccountEntity saveAccount(AccountEntity account);
    Optional<AccountEntity> findById(UUID id);

    List<AccountEntity> findAll();
}
