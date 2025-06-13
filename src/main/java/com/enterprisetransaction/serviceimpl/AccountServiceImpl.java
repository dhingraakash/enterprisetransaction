package com.enterprisetransaction.serviceimpl;

import com.enterprisetransaction.aspect.SaveTransaction;
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

    @Autowired
    private TransactionDao transactionDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Long createUserWithAccount(UserDto dto) {
        User user = EntityMapper.toUser(dto);
        Account acc = EntityMapper.toAccount(dto, user);
        user.setAccount(acc);
        userDao.save(user);
        return acc.getId();
    }

    @Override
    @SaveTransaction(type = TransactionType.DEPOSIT)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deposit(DepositRequestDto request) {
        Account acc = accountDao.findById(request.getToAccountId());
        Helper.validateAccount(acc, request.getToAccountId());

        Helper.credit(acc, request.getAmount());
        accountDao.update(acc);
    }

    @Override
    @SaveTransaction(type = TransactionType.WITHDRAWAL)
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void withdraw(WithdrawRequestDto request) {
        Account acc = accountDao.findById(request.getFromAccountId());
        try {
            Thread.sleep(5000);
        }catch (InterruptedException ex){

        }
        Helper.validateAccount(acc, request.getFromAccountId());
        Helper.validateSufficientBalance(acc, request.getAmount());

        Helper.debit(acc, request.getAmount());
        accountDao.update(acc);
    }

    @Override
    @SaveTransaction(type = TransactionType.TRANSFER)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void transfer(TransferRequestDto request) {
        withdraw(RequestMapper.toWithdrawRequest(request));
        deposit(RequestMapper.toDepositRequest(request));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Double getBalance(Long accountId) {
        Account acc = accountDao.findById(accountId);
        Helper.validateAccount(acc, accountId);
        return acc.getBalance();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Transaction> getHistory(Long accountId) {
        return transactionDao.findByAccountId(accountId);
    }

}
