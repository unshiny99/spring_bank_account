package com.exalt.bank_account_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.exalt.bank_account_test.adapters.out.persistence.AccountRepositoryAdapter;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.domain.service.DomainAccountService;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public class DomainAccountServiceUnitTest {
    private AccountRepositoryAdapter accountRepositoryAdapter;
    private DomainAccountService accountServiceUnitTest;

    @BeforeEach
    void setUp() {
        accountRepositoryAdapter = mock(AccountRepositoryAdapter.class);
        accountServiceUnitTest = new DomainAccountService(accountRepositoryAdapter);
    }

    @Test
    void shouldCreateAccount_thenSaveIt() {
         AccountEntity accountEntity = new AccountEntity();

         AccountEntity savedEntity = new AccountEntity();
         savedEntity.setId(UUID.randomUUID()); // Assume setId method exists
 
         when(accountRepositoryAdapter.saveAccount(any(AccountEntity.class))).thenReturn(savedEntity);
 
         // Call the method under test
         UUID resultId = accountServiceUnitTest.createAccount(accountEntity);
 
         assertNotNull(resultId);
         verify(accountRepositoryAdapter, times(1)).saveAccount(any(AccountEntity.class));
    }

    @Test
    void shouldConsultBalance() {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(100.0);

        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(accountEntity));

        accountServiceUnitTest.consultBalance(accountId);

        verify(accountRepositoryAdapter, times(1)).findById(accountId);
        String balance = accountServiceUnitTest.consultBalance(accountId);
        assertEquals("100.0 €", balance);
    }

    @Test
    void shouldConsultTransactionHistory() {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);

        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(accountEntity));

        accountServiceUnitTest.consultTransactionHistory(accountId);

        verify(accountRepositoryAdapter, times(1)).findById(accountId);
    }

    // Define a method that provides test arguments for deposit cases
    static Stream<Arguments> provideDepositTestCases() {
        return Stream.of(
            Arguments.of(100.0, 50.0, 150.0, true),
            Arguments.of(100.0, -50.0, 100.0, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDepositTestCases")
    void shouldDepositMoney_thenSaveIt(double initialBalance, double depositAmount, double expectedBalance, boolean expectedResult) {
        // Arrange
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);
    
        Transaction depositTransaction = new Transaction(depositAmount);
    
        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepositoryAdapter.saveAccount(any(AccountEntity.class))).thenAnswer(invocation -> {
            AccountEntity updatedEntity = invocation.getArgument(0);
            accountEntity.setBalance(updatedEntity.getBalance());
            return updatedEntity;
        });
    
        // Act
        boolean result = accountServiceUnitTest.depositMoney(accountId, depositTransaction);
    
        // Assert
        assertEquals(expectedResult, result); // Check if the operation result matches the expected result
        assertEquals(expectedBalance, accountEntity.getBalance()); // Verify balance after the operation
        verify(accountRepositoryAdapter, times(expectedResult ? 1 : 0)).saveAccount(any(AccountEntity.class)); // Check if save was called based on the expected result
    }

    // Define a method that provides test arguments for withdrawal cases
    static Stream<Arguments> provideWithdrawTestCases() {
        return Stream.of(
            Arguments.of(100.0, -50.0, 50.0, true),
            Arguments.of(100.0, 50.0, 100.0, false) 
        );
    }

    @ParameterizedTest
    @MethodSource("provideWithdrawTestCases")
    void shouldWithdrawMoney_thenSaveIt(double initialBalance, double withdrawAmount, double expectedBalance, boolean expectedResult) {
        UUID accountId = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);
    
        Transaction withdrawTransaction = new Transaction(withdrawAmount);
    
        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepositoryAdapter.saveAccount(any(AccountEntity.class))).thenAnswer(invocation -> {
            AccountEntity updatedEntity = invocation.getArgument(0);
            accountEntity.setBalance(updatedEntity.getBalance());
            return updatedEntity;
        });

        // Act
        boolean result = accountServiceUnitTest.withdrawMoney(accountId, withdrawTransaction);
    
        assertEquals(expectedResult, result); // Check if the operation result matches the expected result
        assertEquals(expectedBalance, accountEntity.getBalance()); // Verify balance after the operation
        verify(accountRepositoryAdapter, times(expectedResult ? 1 : 0)).saveAccount(any(AccountEntity.class)); // Check if save was called based on the expected result
    }
}
