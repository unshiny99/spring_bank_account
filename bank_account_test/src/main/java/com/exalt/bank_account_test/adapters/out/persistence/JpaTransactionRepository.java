package com.exalt.bank_account_test.adapters.out.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalt.bank_account_test.infrastructure.persistence.entities.TransactionEntity;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    //List<TransactionEntity> findByAccountId(UUID accountId);
}
