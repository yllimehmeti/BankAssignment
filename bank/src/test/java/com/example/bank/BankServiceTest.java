package com.example.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankServiceTest {

    @Autowired
    private BankService bankService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        bankService.initializeBank("Test Bank", 10.0, 5.0);
    }

    @Test
    public void testCreateAccount() {
        
        Account account = bankService.createAccount("John Doe", 1000.00);

        
        assertNotNull(account);
        
        assertNotNull(account.getAccountId());
    }

    @Test
    public void testDeposit() {
        Account account = new Account("John Doe", 1000.00); 
        
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        
        Account updatedAccount = bankService.deposit(account.getAccountId(), 200.00);

        
        assertEquals(1200.00, updatedAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw() {
        Account account = new Account("John Doe", 1000.00);
        
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        
        Account updatedAccount = bankService.withdraw(account.getAccountId(), 200.00);

        
        assertEquals(800.00, updatedAccount.getBalance(), 0.01);
    }

    @Test
    public void testTransfer() {
        Account account1 = new Account("John Doe", 1000.00);
        Account account2 = new Account("Jane Doe", 500.00);
        
        when(accountRepository.save(any(Account.class))).thenReturn(account1).thenReturn(account2);

        
        bankService.transfer(account1.getAccountId(), account2.getAccountId(), 200.00, true);

        
        Account updatedAccount1 = accountRepository.findById(account1.getAccountId()).orElse(null);
        Account updatedAccount2 = accountRepository.findById(account2.getAccountId()).orElse(null);

        
        assertNotNull(updatedAccount1);
        assertNotNull(updatedAccount2);
        assertEquals(800.00, updatedAccount1.getBalance(), 0.01);
        assertEquals(700.00, updatedAccount2.getBalance(), 0.01);
    }
}
