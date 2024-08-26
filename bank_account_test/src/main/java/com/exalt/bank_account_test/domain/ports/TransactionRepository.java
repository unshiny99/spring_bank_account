package com.exalt.bank_account_test.domain.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;
import com.exalt.bank_account_test.infrastructure.persistence.entities.TransactionEntity;

@Repository
public interface TransactionRepository {
    TransactionEntity save(Transaction transaction, AccountEntity accountEntity);
    Optional<Transaction> findById(UUID id);

    List<Transaction> findAll();
    //List<Transaction> findByAccountId(UUID accountId);
}
