package com.exalt.bank_account_test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exalt.bank_account_test.domain.ports.AccountRepository;
import com.exalt.bank_account_test.domain.service.AccountService;
import com.exalt.bank_account_test.domain.service.DomainAccountService;

@Configuration
public class BeanConfiguration {
    private final AccountRepository accountRepository;

    public BeanConfiguration(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    AccountService accountService() {
        return new DomainAccountService(accountRepository);
    }
}
