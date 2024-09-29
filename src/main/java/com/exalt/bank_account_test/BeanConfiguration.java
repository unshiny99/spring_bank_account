package com.exalt.bank_account_test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exalt.bank_account_test.adapters.out.persistence.AccountRepositoryAdapter;
import com.exalt.bank_account_test.adapters.out.persistence.JpaAccountRepository;
import com.exalt.bank_account_test.adapters.out.persistence.JpaTransactionRepository;
import com.exalt.bank_account_test.adapters.out.persistence.TransactionRepositoryAdapter;
import com.exalt.bank_account_test.domain.service.AccountService;
import com.exalt.bank_account_test.domain.service.DomainAccountService;

@Configuration
public class BeanConfiguration {
    @Bean
    AccountService accountService(AccountRepositoryAdapter accountRepositoryAdapter, TransactionRepositoryAdapter transactionRepositoryAdapter) {
        return new DomainAccountService(accountRepositoryAdapter, transactionRepositoryAdapter);
    }

    @Bean
    AccountRepositoryAdapter accountRepositoryAdapter(JpaAccountRepository jpaAccountRepository) {
        return new AccountRepositoryAdapter(jpaAccountRepository);
    }

    @Bean
    TransactionRepositoryAdapter transactionRepositoryAdapter(JpaTransactionRepository jpaTransactionRepository) {
        return new TransactionRepositoryAdapter(jpaTransactionRepository);
    }
}
