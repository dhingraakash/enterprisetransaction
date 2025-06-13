package com.enterprisetransaction.service;

import com.enterprisetransaction.dto.DepositRequestDto;
import com.enterprisetransaction.dto.TransactionSummaryDto;
import com.enterprisetransaction.dto.WithdrawRequestDto;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    void logDeposit(DepositRequestDto dto, TransactionType type);
    void logWithdraw(WithdrawRequestDto dto, TransactionType type);
    List<TransactionSummaryDto> getTransactionSummary();
    List<Transaction> getHistory(Long accountId);
}
