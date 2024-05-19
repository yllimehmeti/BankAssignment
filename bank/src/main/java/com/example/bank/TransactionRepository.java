package com.example.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOriginatingAccountIdOrResultingAccountId(Long originatingAccountId, Long resultingAccountId);
}


