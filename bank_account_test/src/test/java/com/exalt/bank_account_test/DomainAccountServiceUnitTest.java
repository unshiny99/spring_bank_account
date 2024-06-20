package com.exalt.bank_account_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.domain.service.DomainAccountService;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public class DomainAccountServiceUnitTest {
    private AccountRepository accountRepository;
    private DomainAccountService accountServiceUnitTest;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountServiceUnitTest = new DomainAccountService(accountRepository);
    }

    @Test
    void shouldCreateAccount_thenSaveIt() {
         AccountEntity accountEntity = new AccountEntity();

         AccountEntity savedEntity = new AccountEntity();
         savedEntity.setId(UUID.randomUUID()); // Assume setId method exists
 
         when(accountRepository.save(any(AccountEntity.class))).thenReturn(savedEntity);
 
         // Call the method under test
         UUID resultId = accountServiceUnitTest.createAccount(accountEntity);
 
         assertNotNull(resultId);
         verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void shouldConsultBalance() {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(100.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        accountServiceUnitTest.consultBalance(accountId);

        verify(accountRepository, times(1)).findById(accountId);
        String balance = accountServiceUnitTest.consultBalance(accountId);
        assertEquals("100.0 €", balance);
    }

    @Test
    void shouldConsultTransactionHistory() {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        accountServiceUnitTest.consultTransactionHistory(accountId);

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void shouldDepositMoney_thenSaveIt() {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(100.0);

        Transaction depositTransaction = new Transaction(50.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenAnswer(invocation -> {
            AccountEntity updatedEntity = invocation.getArgument(0);
            accountEntity.setBalance(updatedEntity.getBalance());
            return updatedEntity;
        });

        boolean result = accountServiceUnitTest.depositMoney(accountId, depositTransaction);

        assertTrue(result);
        assertEquals(150.0, accountEntity.getBalance());
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void shouldNotDepositMoney_whenDepositFails() {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(100.0);

        Transaction depositTransaction = new Transaction(-50.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        boolean result = accountServiceUnitTest.depositMoney(accountId, depositTransaction);

        assertFalse(result);
        assertEquals(100.0, accountEntity.getBalance());
        verify(accountRepository, times(0)).save(any(AccountEntity.class));
    }

    @Test
    void shouldWithdrawMoney_thenSaveIt() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(100.0);

        Transaction withdrawTransaction = new Transaction(-50.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenAnswer(invocation -> {
            AccountEntity updatedEntity = invocation.getArgument(0);
            accountEntity.setBalance(updatedEntity.getBalance());
            return updatedEntity;
        });

        // Act
        boolean result = accountServiceUnitTest.withdrawMoney(accountId, withdrawTransaction);

        // Assert
        assertTrue(result);
        assertEquals(50.0, accountEntity.getBalance());
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void shouldNotWithdrawMoney_whenWithdrawFails() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(100.0);

        // try to withdraw a positive value
        Transaction withdrawTransaction = new Transaction(50.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        boolean result = accountServiceUnitTest.withdrawMoney(accountId, withdrawTransaction);

        // Assert
        assertFalse(result);
        assertEquals(100.0, accountEntity.getBalance());
        verify(accountRepository, times(0)).save(any(AccountEntity.class));
    }
}
