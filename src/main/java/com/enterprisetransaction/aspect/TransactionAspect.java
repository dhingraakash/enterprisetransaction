package com.enterprisetransaction.aspect;

import com.enterprisetransaction.anotations.SaveTransaction;
import com.enterprisetransaction.dto.DepositRequestDto;
import com.enterprisetransaction.dto.RequestMapper;
import com.enterprisetransaction.dto.TransferRequestDto;
import com.enterprisetransaction.dto.WithdrawRequestDto;
import com.enterprisetransaction.enums.TransactionType;
import com.enterprisetransaction.service.TransactionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAspect {
    @Autowired
    private TransactionService logService;

    @AfterReturning("@annotation(com.enterprisetransaction.anotations.SaveTransaction)")
    public void logTransaction(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SaveTransaction annotation = signature.getMethod().getAnnotation(SaveTransaction.class);
        TransactionType type = annotation.type();

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof DepositRequestDto dto && type == TransactionType.DEPOSIT) {
                logService.logDeposit(dto, TransactionType.DEPOSIT);
            } else if (arg instanceof WithdrawRequestDto dto && type == TransactionType.WITHDRAWAL) {
                logService.logWithdraw(dto, TransactionType.WITHDRAWAL);
            } else if (arg instanceof TransferRequestDto dto && type == TransactionType.TRANSFER) {
                // Log two entries: one for sender, one for receiver
                logService.logWithdraw(RequestMapper.toWithdrawRequest(dto), TransactionType.WITHDRAWAL);
                logService.logDeposit(RequestMapper.toDepositRequest(dto), TransactionType.DEPOSIT);
            }
        }
    }
}
