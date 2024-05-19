package com.example.bank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String userName;
    private double balance;

    public Account() {}
    
    public Account(String userName, double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        this.balance -= amount;
    }

    public void transfer(Account targetAccount, double amount, double fee) {
        if (amount + fee > this.balance) {
            throw new IllegalArgumentException("Insufficient funds for transfer and fee.");
        }
        this.balance -= (amount + fee);
        targetAccount.deposit(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
