package com.exalt.bank_account_test;

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

import com.exalt.bank_account_test.adapters.repository.AccountRepository;
import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.service.DomainAccountService;

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
        final UUID id = accountServiceUnitTest.createAccount();
        verify(accountRepository).save(any(Account.class));
        assertNotNull(id);
    }

    @Test
    void shouldConsultBalance() {
        UUID accountId = UUID.randomUUID();
        Account account = mock(Account.class);

        when(account.getId()).thenReturn(accountId);
        when(account.getBalance()).thenReturn(100.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountServiceUnitTest.consultBalance(accountId);

        verify(account, times(1)).getBalance();
    }

    @Test
    void shouldConsultTransactionHistory() {
        Account account = mock(Account.class);
        UUID accountId = account.getId();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountServiceUnitTest.consultTransactionHistory(accountId);

        verify(account, times(1)).getTransactions();
    }

    @Test
    void shouldDepositMoney_thenSaveIt() {
        Account account = mock(Account.class);
        UUID accountId = account.getId();
        Transaction transaction = new Transaction(300.00);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(account.depositMoney(transaction)).thenReturn(true);

        assertTrue(accountServiceUnitTest.depositMoney(accountId, transaction));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void shouldNotDepositMoney_whenDepositFails() {
        Account account = mock(Account.class);
        UUID accountId = account.getId();
        Transaction transaction = new Transaction(-100.00);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(account.depositMoney(transaction)).thenReturn(false);

        assertFalse(accountServiceUnitTest.depositMoney(accountId, transaction));
        verify(accountRepository, never()).save(account);
    }

    @Test
    void shouldWithdrawMoney_thenSaveIt() {
        Account account = mock(Account.class);
        UUID accountId = UUID.randomUUID();
        when(account.getId()).thenReturn(accountId);
        Transaction transaction = new Transaction(50.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(account.withdrawMoney(transaction)).thenReturn(true);

        boolean result = accountServiceUnitTest.withdrawMoney(accountId, transaction);
        assertTrue(result);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void shouldNotWithdrawMoney_whenWithdrawFails() {
        Account account = mock(Account.class);
        UUID accountId = UUID.randomUUID();
        when(account.getId()).thenReturn(accountId);
        Transaction transaction = new Transaction(-50.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(account.withdrawMoney(transaction)).thenReturn(false);

        boolean result = accountServiceUnitTest.withdrawMoney(accountId, transaction);

        assertFalse(result);
        verify(accountRepository, never()).save(account);
    }
}
