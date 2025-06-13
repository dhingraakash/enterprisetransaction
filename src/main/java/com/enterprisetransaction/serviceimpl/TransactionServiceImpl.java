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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private AccountDao accountDao;

    /**
     * Saves a deposit transaction for the given account.
     *
     * @param dto
     * @param type
     */
    public void logDeposit(DepositRequestDto dto, TransactionType type) {
        Account acc = accountDao.findById(dto.getToAccountId());
        Transaction tx = Helper.buildTransaction(acc, dto.getAmount(), type);
        transactionDao.save(tx);
    }

    /**
     * Saves a withdrawal transaction for the given account.
     *
     * @param dto
     * @param type
     */
    public void logWithdraw(WithdrawRequestDto dto, TransactionType type) {
        Account acc = accountDao.findById(dto.getFromAccountId());
        Transaction tx = Helper.buildTransaction(acc, dto.getAmount(), type);
        transactionDao.save(tx);
    }

    /**
     * Returns a summary of all transactions.
     *
     * @return
     */
    public List<TransactionSummaryDto> getTransactionSummary() {
        return transactionDao.getSummaryReport();
    }

    /**
     * Returns transaction history for the given account.
     *
     * @param accountId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Transaction> getHistory(Long accountId) {
        return transactionDao.findByAccountId(accountId);
    }
}
