package com.exalt.bank_account_test.repositories;

import java.util.Optional;
import java.util.UUID;

import com.exalt.bank_account_test.domain.Account;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findById(UUID id);
}
