package com.enterprisetransaction.service;

import com.enterprisetransaction.dto.DepositRequestDto;
import com.enterprisetransaction.dto.TransferRequestDto;
import com.enterprisetransaction.dto.UserDto;
import com.enterprisetransaction.dto.WithdrawRequestDto;
import com.enterprisetransaction.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    Long createUserWithAccount(UserDto dto);

    void deposit(DepositRequestDto request);

    void withdraw(WithdrawRequestDto request);

    void transfer(TransferRequestDto request);

    Double getBalance(Long accountId);
}
