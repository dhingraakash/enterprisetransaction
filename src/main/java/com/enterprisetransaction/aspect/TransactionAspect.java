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
    public void saveTransactionAfterMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SaveTransaction annotation = signature.getMethod().getAnnotation(SaveTransaction.class);
        TransactionType type = annotation.type();

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof DepositRequestDto dto) {
                logService.logDeposit(dto, type);
            } else if (arg instanceof WithdrawRequestDto dto) {
                logService.logWithdraw(dto, type);
            }
        }
    }

    @AfterReturning("execution(* com.enterprisetransaction.service.AccountService.transfer(..)) && args(request)")
    public void logTransferTransactions(TransferRequestDto request) {
        logService.logWithdraw(RequestMapper.toWithdrawRequest(request), TransactionType.WITHDRAWAL);
        logService.logDeposit(RequestMapper.toDepositRequest(request), TransactionType.DEPOSIT);
    }
}
