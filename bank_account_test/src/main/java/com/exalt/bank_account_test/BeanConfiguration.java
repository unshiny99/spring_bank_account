package com.exalt.bank_account_test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exalt.bank_account_test.adapters.out.persistence.AccountRepositoryAdapter;
import com.exalt.bank_account_test.adapters.out.persistence.JpaAccountRepository;
import com.exalt.bank_account_test.domain.service.AccountService;
import com.exalt.bank_account_test.domain.service.DomainAccountService;

@Configuration
public class BeanConfiguration {
    @Bean
    AccountService accountService(AccountRepositoryAdapter accountRepositoryAdapter) {
        return new DomainAccountService(accountRepositoryAdapter);
    }

    @Bean
    AccountRepositoryAdapter accountRepositoryAdapter(JpaAccountRepository jpaAccountRepository) {
        return new AccountRepositoryAdapter(jpaAccountRepository);
    }
}
