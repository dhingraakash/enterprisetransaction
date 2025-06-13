package com.enterprisetransaction.dto;

import com.enterprisetransaction.enums.TransactionType;

public class TransactionSummaryDto {

    private TransactionType type;
    private Long count;
    private Double totalAmount;

    public TransactionSummaryDto() {
    }

    public TransactionSummaryDto(TransactionType type, Long count, Double totalAmount) {
        this.type = type;
        this.count = count;
        this.totalAmount = totalAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
