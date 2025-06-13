package com.enterprisetransaction;

import com.enterprisetransaction.dto.DepositRequestDto;
import com.enterprisetransaction.dto.TransferRequestDto;
import com.enterprisetransaction.dto.WithdrawRequestDto;
import com.enterprisetransaction.entity.Account;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.entity.User;
import com.enterprisetransaction.repository.AccountDao;
import com.enterprisetransaction.repository.TransactionDao;
import com.enterprisetransaction.repository.UserDao;
import com.enterprisetransaction.serviceimpl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private AccountDao accountDao;

    @Mock
    private TransactionDao transactionDao;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");

        account = new Account();
        account.setId(1L);
        account.setBalance(10000.0);
        account.setUser(user);
    }

    @Test
    void testDeposit() {
        DepositRequestDto request = new DepositRequestDto(1L, 2000.0);
        when(accountDao.findById(1L)).thenReturn(account);
        accountService.deposit(request);
        verify(accountDao).update(account);
        assertEquals(12000.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        WithdrawRequestDto request = new WithdrawRequestDto(1L, 3000.0);
        when(accountDao.findById(1L)).thenReturn(account);
        accountService.withdraw(request);
        verify(accountDao).update(account);
        assertEquals(7000.0, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        WithdrawRequestDto request = new WithdrawRequestDto(1L, 15000.0);
        when(accountDao.findById(1L)).thenReturn(account);
        Exception ex = assertThrows(RuntimeException.class, () -> accountService.withdraw(request));
        assertTrue(ex.getMessage().contains("Insufficient balance"));
    }

    @Test
    void testTransfer() {
        TransferRequestDto request = new TransferRequestDto(1L, 2L, 1000.0);
        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(5000.0);

        when(accountDao.findById(1L)).thenReturn(account);
        when(accountDao.findById(2L)).thenReturn(receiver);

        accountService.transfer(request);

        verify(accountDao, times(2)).update(any(Account.class));
        assertEquals(9000.0, account.getBalance());
        assertEquals(6000.0, receiver.getBalance());
    }

    @Test
    void testGetBalance() {
        when(accountDao.findById(1L)).thenReturn(account);
        Double balance = accountService.getBalance(1L);
        assertEquals(10000.0, balance);
    }
}
