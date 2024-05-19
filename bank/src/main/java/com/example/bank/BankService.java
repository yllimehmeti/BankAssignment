package com.example.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private double totalTransactionFeeAmount = 0;
    private double totalTransferAmount = 0;
    private double flatFeeAmount;
    private double percentFeeValue;

    public BankService() {}

    public void initializeBank(String bankName, double flatFeeAmount, double percentFeeValue) {
        this.flatFeeAmount = flatFeeAmount;
        this.percentFeeValue = percentFeeValue;
    }

    public Account createAccount(String userName, double initialBalance) {
        Account account = new Account(userName, initialBalance);
        return accountRepository.save(account);
    }

    public Account deposit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
        account.deposit(amount);
        transactionRepository.save(new Transaction(amount, accountId, accountId, "Deposit"));
        return accountRepository.save(account);
    }

    public Account withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
        account.withdraw(amount);
        transactionRepository.save(new Transaction(amount, accountId, accountId, "Withdrawal"));
        return accountRepository.save(account);
    }

    public void transfer(Long fromAccountId, Long toAccountId, double amount, boolean isFlatFee) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Originating account not found."));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Resulting account not found."));
        double fee = isFlatFee ? flatFeeAmount : amount * percentFeeValue / 100;
        fromAccount.transfer(toAccount, amount, fee);
        transactionRepository.save(new Transaction(amount, fromAccountId, toAccountId, "Transfer"));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        totalTransactionFeeAmount += fee;
        totalTransferAmount += amount;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByOriginatingAccountIdOrResultingAccountId(accountId, accountId);
    }

    public double getTotalTransactionFeeAmount() {
        return totalTransactionFeeAmount;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }
}
