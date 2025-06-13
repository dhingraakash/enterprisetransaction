package com.enterprisetransaction.controller;

import com.enterprisetransaction.dto.DepositRequestDto;
import com.enterprisetransaction.dto.TransferRequestDto;
import com.enterprisetransaction.dto.UserDto;
import com.enterprisetransaction.dto.WithdrawRequestDto;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.serviceimpl.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountServiceImpl service;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserDto dto) {
        Long accountId = service.createUserWithAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully. Account Number: " + accountId);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequestDto req) {
        service.deposit(req);
        return ResponseEntity.ok("Deposited");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody @Valid WithdrawRequestDto req){
        service.withdraw(req);
        return ResponseEntity.ok("Withdrawn");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDto req) {
        service.transfer(req);
        return ResponseEntity.ok("Transferred");
    }

    @GetMapping("/history/{id}")
    public List<Transaction> history(@PathVariable Long id) {
        return service.getHistory(id);
    }

    @GetMapping("/balance/{id}")
    public Double balance(@PathVariable Long id) {
        return service.getBalance(id);
    }
}
