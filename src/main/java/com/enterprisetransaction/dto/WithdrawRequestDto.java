package com.enterprisetransaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class WithdrawRequestDto {
    @NotNull(message = "Account ID is required")
    private Long fromAccountId;

    @DecimalMin(value = "1", message = "Amount must be greater than zero")
    private double amount;

    public WithdrawRequestDto() {
    }

    public WithdrawRequestDto(Long fromAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
