package com.exalt.bank_account_test.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.ports.TransactionRepository;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;
import com.exalt.bank_account_test.infrastructure.persistence.entities.TransactionEntity;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private JpaTransactionRepository jpaTransactionRepository;

    public TransactionRepositoryAdapter(JpaTransactionRepository jpaTransactionRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return jpaTransactionRepository.findAll()
            .stream()
            .map(TransactionEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return jpaTransactionRepository.findByAccountId(accountId)
            .stream()
            .map(TransactionEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public TransactionEntity save(Transaction transaction, AccountEntity accountEntity) {
        TransactionEntity transactionEntity = new TransactionEntity(transaction, accountEntity);
        return jpaTransactionRepository.save(transactionEntity);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpaTransactionRepository.findById(id)
            .map(TransactionEntity::toDomain);
    }
}
