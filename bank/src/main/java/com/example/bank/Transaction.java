package com.example.bank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private double amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String transactionReason;

    public Transaction() {}

    public Transaction(double amount, Long originatingAccountId, Long resultingAccountId, String transactionReason) {
        this.amount = amount;
        this.originatingAccountId = originatingAccountId;
        this.resultingAccountId = resultingAccountId;
        this.transactionReason = transactionReason;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", originatingAccountId=" + originatingAccountId +
                ", resultingAccountId=" + resultingAccountId +
                ", transactionReason='" + transactionReason + '\'' +
                '}';
    }
}


