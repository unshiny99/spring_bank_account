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
import com.exalt.bank_account_test.adapters.out.persistence.TransactionRepositoryAdapter;
import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.service.DomainAccountService;
import com.exalt.bank_account_test.infrastructure.persistence.entities.AccountEntity;

public class DomainAccountServiceUnitTest {
    private AccountRepositoryAdapter accountRepositoryAdapter;
    private TransactionRepositoryAdapter transactionRepositoryAdapter;
    private DomainAccountService accountServiceUnitTest;

    @BeforeEach
    void setUp() {
        accountRepositoryAdapter = mock(AccountRepositoryAdapter.class);
        accountServiceUnitTest = new DomainAccountService(accountRepositoryAdapter, transactionRepositoryAdapter);
    }

    @Test
    void shouldCreateAccount_thenSaveIt() {
         Account account = new Account();

         AccountEntity savedEntity = new AccountEntity(account);
         savedEntity.setId(UUID.randomUUID()); // Assume setId method exists
 
         when(accountRepositoryAdapter.saveAccount(any(Account.class))).thenReturn(savedEntity);
 
         // Call the method under test
         UUID resultId = accountServiceUnitTest.createAccount(account);
 
         assertNotNull(resultId);
         verify(accountRepositoryAdapter, times(1)).saveAccount(any(Account.class));
    }

    @Test
    void shouldConsultBalance() {
        Account account = new Account();
        account.setBalance(100.0);

        UUID accountId = UUID.randomUUID();
        AccountEntity savedEntity = new AccountEntity(account);
        savedEntity.setId(accountId);

        // Mock the findById method to return the savedEntity when the accountId is passed
        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(savedEntity));

        double balance = accountServiceUnitTest.consultBalance(accountId);

        verify(accountRepositoryAdapter, times(1)).findById(accountId);

        assertEquals(100.0, balance);
    }

    @Test
    void shouldConsultTransactionHistory() {
        Account account = new Account();
        AccountEntity savedEntity = new AccountEntity(account);
        UUID accountId = UUID.randomUUID();
        savedEntity.setId(accountId);

        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(savedEntity));

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
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(initialBalance);

        AccountEntity savedEntity = new AccountEntity(account);
        savedEntity.setId(accountId);
    
        Transaction depositTransaction = new Transaction(depositAmount);
    
        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(savedEntity));
    
        // Act
        boolean result = accountServiceUnitTest.depositMoney(accountId, depositTransaction);
        // Retrieve the updated account entity
        AccountEntity updatedEntity = accountRepositoryAdapter.findById(accountId).get();
    
        // Assert
        assertEquals(expectedResult, result); // Check if the operation result matches the expected result
        assertEquals(expectedBalance, updatedEntity.getBalance()); // Verify balance after the operation
        verify(accountRepositoryAdapter, times(expectedResult ? 1 : 0)).saveAccount(any(Account.class)); // Check if save was called based on the expected result
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
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(initialBalance);

        AccountEntity savedEntity = new AccountEntity(account);
        savedEntity.setId(accountId);
    
        Transaction withdrawTransaction = new Transaction(withdrawAmount);
    
        when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(savedEntity));
        /*
        when(accountRepositoryAdapter.saveAccount(any(Account.class))).thenAnswer(invocation -> {
            Account updatedEntity = invocation.getArgument(0);
            account.setBalance(updatedEntity.getBalance());
            return updatedEntity;
        });
         */

        // Act
        boolean result = accountServiceUnitTest.withdrawMoney(accountId, withdrawTransaction);
        // Retrieve the updated account entity
        AccountEntity updatedEntity = accountRepositoryAdapter.findById(accountId).get();
    
        assertEquals(expectedResult, result); // Check if the operation result matches the expected result
        assertEquals(expectedBalance, updatedEntity.getBalance()); // Verify balance after the operation
        verify(accountRepositoryAdapter, times(expectedResult ? 1 : 0)).saveAccount(any(Account.class)); // Check if save was called based on the expected result
    }
}
