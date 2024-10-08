package com.exalt.bank_account_test.adapters.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exalt.bank_account_test.domain.model.Account;
import com.exalt.bank_account_test.domain.model.Transaction;
import com.exalt.bank_account_test.domain.service.AccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        try {
            UUID id = accountService.createAccount(account);
            String message = String.format("Account well created : %s", id);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch(Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        try {
            List<Account> accounts = accountService.getAccounts();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/{id}/balance")
    public ResponseEntity<String> getBalance(@PathVariable UUID id) {
        try {
            double balance = accountService.consultBalance(id);
            String message = "Balance : " + balance + " €";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable UUID id) {
        try {
            List<Transaction> transactions = accountService.consultTransactionHistory(id);

            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @PostMapping("/{id}/transactions")
    public ResponseEntity<String> withdrawMoney(@PathVariable UUID id, @RequestParam String operationType, @RequestBody Transaction transaction) {
        if(operationType.equals("withdraw")) {
            try {
                boolean result = accountService.withdrawMoney(id, transaction);
                if(! result) {
                    return new ResponseEntity<>("An error occured while withdrawing money. Please check the parameters.", HttpStatus.BAD_REQUEST);
                }
    
                String message = String.format("Money well withdrawed in %s", id);
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            } catch(Exception exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if(operationType.equals("deposit")) {
            try {
                boolean result = accountService.depositMoney(id, transaction);
                if(! result) {
                    return new ResponseEntity<>("An error occured while depositing money. Please check the parameters.", HttpStatus.BAD_REQUEST);
                }
    
                String message = String.format("Money well deposited in %s", id);
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            } catch(Exception exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("The operationType attribute is incorrect. Authorized values : deposit or withdraw.", HttpStatus.BAD_REQUEST);
        }
    }
}
