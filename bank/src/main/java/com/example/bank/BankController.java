package com.example.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/initialize")
    public void initializeBank(@RequestParam String bankName, @RequestParam double flatFee, @RequestParam double percentFee) {
        bankService.initializeBank(bankName, flatFee, percentFee);
    }

    @PostMapping("/createAccount")
    public Account createAccount(@RequestParam String userName, @RequestParam double initialBalance) {
        return bankService.createAccount(userName, initialBalance);
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestParam Long accountId, @RequestParam double amount) {
        return bankService.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam Long accountId, @RequestParam double amount) {
        return bankService.withdraw(accountId, amount);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam double amount, @RequestParam boolean isFlatFee) {
        bankService.transfer(fromAccountId, toAccountId, amount, isFlatFee);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam Long accountId) {
        return bankService.getTransactions(accountId);
    }

    @GetMapping("/totalTransactionFees")
    public double getTotalTransactionFeeAmount() {
        return bankService.getTotalTransactionFeeAmount();
    }

    @GetMapping("/totalTransferAmount")
    public double getTotalTransferAmount() {
        return bankService.getTotalTransferAmount();
    }
}
