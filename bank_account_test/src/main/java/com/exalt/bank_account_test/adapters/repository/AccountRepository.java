package com.exalt.bank_account_test.adapters.repository;

import java.util.Optional;
import java.util.UUID;

import com.exalt.bank_account_test.domain.model.Account;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findById(UUID id);
}
