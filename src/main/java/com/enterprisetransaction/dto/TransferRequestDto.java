package com.enterprisetransaction.dto;

import jakarta.validation.constraints.NotNull;

public class TransferRequestDto {
    @NotNull
    private Long fromAccountId;
    @NotNull
    private Long toAccountId;
    private Double amount;

    public TransferRequestDto() {
    }

    public TransferRequestDto(Long fromAccountId, Long toAccountId, Double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
