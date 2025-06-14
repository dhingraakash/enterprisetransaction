package com.enterprisetransaction.serviceimpl;

import com.enterprisetransaction.anotations.SaveTransaction;
import com.enterprisetransaction.dto.*;
import com.enterprisetransaction.entity.Account;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.entity.User;
import com.enterprisetransaction.enums.TransactionType;
import com.enterprisetransaction.helper.Helper;
import com.enterprisetransaction.repository.AccountDao;
import com.enterprisetransaction.repository.TransactionDao;
import com.enterprisetransaction.repository.UserDao;
import com.enterprisetransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;


    /**
     * Creates a new user and an associated account within a transactional context.
     * Rolls back if any exception occurs to ensure data consistency.
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Long createUserWithAccount(UserDto dto) {
        User user = EntityMapper.toUser(dto);
        Account acc = EntityMapper.toAccount(dto, user);
        user.setAccount(acc);
        userDao.save(user);
        return acc.getId();
    }

    /**
     * Deposits the specified amount into the given account.
     * Ensures account validation and updates the balance within a transaction.
     *
     * @param request
     */
    @Override
    @SaveTransaction(type = TransactionType.DEPOSIT)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deposit(DepositRequestDto request) {
        Account acc = accountDao.findById(request.getToAccountId());
        Helper.validateAccount(acc, request.getToAccountId());

        Helper.credit(acc, request.getAmount());
        accountDao.update(acc);
    }

    /**
     * Withdraws the specified amount from the given account.
     * Validates account existence and balance before debiting.
     * Executes in a new transaction with READ_COMMITTED isolation.
     *
     * @param request
     */
    @Override
    @SaveTransaction(type = TransactionType.WITHDRAWAL)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void withdraw(WithdrawRequestDto request) {
        Account acc = accountDao.findById(request.getFromAccountId());
        Helper.validateAccount(acc, request.getFromAccountId());
        Helper.validateSufficientBalance(acc, request.getAmount());

        Helper.debit(acc, request.getAmount());
        accountDao.update(acc);
    }

    /**
     * Transfers a specified amount from one account to another.
     * Performs withdrawal and deposit in a single transaction to ensure consistency.
     * Uses REPEATABLE_READ isolation to prevent non-repeatable reads during transfer.
     *
     * @param request
     */
    @Override
    @SaveTransaction(type = TransactionType.TRANSFER)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void transfer(TransferRequestDto request) {
        Account from = accountDao.findById(request.getFromAccountId());
        Account to = accountDao.findById(request.getToAccountId());

        Helper.validateAccount(from, request.getFromAccountId());
        Helper.validateAccount(to, request.getToAccountId());
        Helper.validateSufficientBalance(from, request.getAmount());

        Helper.debit(from, request.getAmount());
        Helper.credit(to, request.getAmount());

        accountDao.update(from);
        accountDao.update(to);
    }

    /**
     * Retrieves the current balance of the specified account.
     * Use Supports propagation as it's a read-only operation.
     *
     * @param accountId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Double getBalance(Long accountId) {
        Account acc = accountDao.findById(accountId);
        Helper.validateAccount(acc, accountId);
        return acc.getBalance();
    }
}
