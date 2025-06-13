package com.enterprisetransaction.controller;

import com.enterprisetransaction.dto.TransactionSummaryDto;
import com.enterprisetransaction.entity.Transaction;
import com.enterprisetransaction.service.TransactionService;
import com.enterprisetransaction.serviceimpl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping("/summary")
    public List<TransactionSummaryDto> getTransactionSummary() {
        return service.getTransactionSummary();
    }

    @GetMapping("/history/{accountId}")
    public List<Transaction> history(@PathVariable("accountId") Long id) {
        return service.getHistory(id);
    }

}
