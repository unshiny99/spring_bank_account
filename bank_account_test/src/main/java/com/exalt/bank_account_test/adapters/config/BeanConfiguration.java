package com.exalt.bank_account_test.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exalt.bank_account_test.adapters.repository.AccountRepository;
import com.exalt.bank_account_test.domain.service.AccountService;
import com.exalt.bank_account_test.domain.service.DomainAccountService;

@Configuration
public class BeanConfiguration {
    
    @Bean
    AccountService accountService(AccountRepository accountRepository) {
        return new DomainAccountService(accountRepository);
    }
}
