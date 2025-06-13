package com.enterprisetransaction.helper;

import com.enterprisetransaction.entity.Account;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.enums.TransactionType;
import com.enterprisetransaction.exception.AccountNotFoundException;
import com.enterprisetransaction.exception.InsufficientBalanceException;

import java.time.LocalDateTime;

public class Helper {
    public static Transaction buildTransaction(Account acc, double amount, TransactionType type) {
        Transaction tx = new Transaction();
        tx.setAccount(acc);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setTimestamp(LocalDateTime.now());
        return tx;
    }

    public static void validateAccount(Account acc, Long accountId) {
        if (acc == null) {
            throw new AccountNotFoundException("Account with ID " + accountId + " not found");
        }
    }

    public static void validateSufficientBalance(Account acc, double amount) {
        if (acc.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance in account " + acc.getId());
        }
    }

    public static void credit(Account acc, double amount) {
        acc.setBalance(acc.getBalance() + amount);
    }

    public static void debit(Account acc, double amount) {
        acc.setBalance(acc.getBalance() - amount);
    }

}
