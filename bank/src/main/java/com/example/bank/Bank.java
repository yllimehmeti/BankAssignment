package com.example.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bank {
    private String bankName;
    private double flatFeeAmount;
    private double percentFeeValue;
    private double totalTransactionFeeAmount = 0;
    private double totalTransferAmount = 0;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Bank() {}

    public Bank(String bankName, double flatFeeAmount, double percentFeeValue) {
        this.bankName = bankName;
        this.flatFeeAmount = flatFeeAmount;
        this.percentFeeValue = percentFeeValue;
    }

    public Account createAccount(String userName, double initialBalance) {
        Account account = new Account(userName, initialBalance);
        return accountRepository.save(account);
    }

    public void deposit(Account account, double amount) {
        account.deposit(amount);
        accountRepository.save(account);
        transactionRepository.save(new Transaction(amount, account.getAccountId(), account.getAccountId(), "Deposit"));
    }

    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
        accountRepository.save(account);
        transactionRepository.save(new Transaction(amount, account.getAccountId(), account.getAccountId(), "Withdrawal"));
    }

    public void transfer(Account fromAccount, Account toAccount, double amount) {
        double fee = calculateFee(amount);
        fromAccount.transfer(toAccount, amount, fee);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        totalTransactionFeeAmount += fee;
        totalTransferAmount += amount;
        transactionRepository.save(new Transaction(amount, fromAccount.getAccountId(), toAccount.getAccountId(), "Transfer"));
    }

    public double getAccountBalance(Account account) {
        return account.getBalance();
    }

    public double getTotalTransactionFeeAmount() {
        return totalTransactionFeeAmount;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }

    private double calculateFee(double amount) {
        return (amount * percentFeeValue / 100) + flatFeeAmount;
    }
}
