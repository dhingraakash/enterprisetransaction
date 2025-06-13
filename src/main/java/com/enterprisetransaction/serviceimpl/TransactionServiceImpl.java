package com.enterprisetransaction.serviceimpl;

import com.enterprisetransaction.dto.DepositRequestDto;
import com.enterprisetransaction.dto.TransactionSummaryDto;
import com.enterprisetransaction.dto.WithdrawRequestDto;
import com.enterprisetransaction.entity.Account;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.enums.TransactionType;
import com.enterprisetransaction.helper.Helper;
import com.enterprisetransaction.repository.AccountDao;
import com.enterprisetransaction.repository.TransactionDao;
import com.enterprisetransaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private AccountDao accountDao;

    public void logDeposit(DepositRequestDto dto, TransactionType type) {
        Account acc = accountDao.findById(dto.getToAccountId());
        Transaction tx = Helper.buildTransaction(acc, dto.getAmount(), type);
        transactionDao.save(tx);
    }

    public void logWithdraw(WithdrawRequestDto dto, TransactionType type) {
        Account acc = accountDao.findById(dto.getFromAccountId());
        Transaction tx = Helper.buildTransaction(acc, dto.getAmount(), type);
        transactionDao.save(tx);
    }

    public List<TransactionSummaryDto> getTransactionSummary() {
        return transactionDao.getSummaryReport();
    }
}
