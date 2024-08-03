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
        UUID accountId = UUID.randomUUID();
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(100.0);

        //when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(account));

        accountServiceUnitTest.consultBalance(accountId);

        verify(accountRepositoryAdapter, times(1)).findById(accountId);
        double balance = accountServiceUnitTest.consultBalance(accountId);
        assertEquals(100.0, balance);
    }

    @Test
    void shouldConsultTransactionHistory() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account();
        account.setId(accountId);

        //when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(account));

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
    
        Transaction depositTransaction = new Transaction(depositAmount);
    
        //when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepositoryAdapter.saveAccount(any(Account.class))).thenAnswer(invocation -> {
            Account updatedAccount = invocation.getArgument(0);
            account.setBalance(updatedAccount.getBalance());
            return updatedAccount;
        });
    
        // Act
        boolean result = accountServiceUnitTest.depositMoney(accountId, depositTransaction);
    
        // Assert
        assertEquals(expectedResult, result); // Check if the operation result matches the expected result
        assertEquals(expectedBalance, account.getBalance()); // Verify balance after the operation
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
    
        Transaction withdrawTransaction = new Transaction(withdrawAmount);
    
        //when(accountRepositoryAdapter.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepositoryAdapter.saveAccount(any(Account.class))).thenAnswer(invocation -> {
            Account updatedEntity = invocation.getArgument(0);
            account.setBalance(updatedEntity.getBalance());
            return updatedEntity;
        });

        // Act
        boolean result = accountServiceUnitTest.withdrawMoney(accountId, withdrawTransaction);
    
        assertEquals(expectedResult, result); // Check if the operation result matches the expected result
        assertEquals(expectedBalance, account.getBalance()); // Verify balance after the operation
        verify(accountRepositoryAdapter, times(expectedResult ? 1 : 0)).saveAccount(any(Account.class)); // Check if save was called based on the expected result
    }
}
