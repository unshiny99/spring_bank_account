package com.exalt.bank_account_test.adapters.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID>, AccountRepository {

    @Override
    Optional<AccountEntity> findById(UUID id);
}
