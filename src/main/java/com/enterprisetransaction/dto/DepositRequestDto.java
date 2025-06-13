package com.enterprisetransaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class DepositRequestDto {
    @NotNull(message = "Account ID is required")
    private Long toAccountId;

    @DecimalMin(value = "1", message = "Amount must be greater than 100")
    private double amount;

    public DepositRequestDto() {
    }

    public DepositRequestDto(Long toAccountId, double amount) {
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
