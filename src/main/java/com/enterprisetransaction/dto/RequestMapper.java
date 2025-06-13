package com.enterprisetransaction.dto;

public class RequestMapper {
    public static WithdrawRequestDto toWithdrawRequest(TransferRequestDto request) {
        WithdrawRequestDto withdrawRequest = new WithdrawRequestDto();
        withdrawRequest.setFromAccountId(request.getFromAccountId());
        withdrawRequest.setAmount(request.getAmount());
        return withdrawRequest;
    }

    public static DepositRequestDto toDepositRequest(TransferRequestDto request) {
        DepositRequestDto depositRequest = new DepositRequestDto();
        depositRequest.setToAccountId(request.getToAccountId());
        depositRequest.setAmount(request.getAmount());
        return depositRequest;
    }

}
