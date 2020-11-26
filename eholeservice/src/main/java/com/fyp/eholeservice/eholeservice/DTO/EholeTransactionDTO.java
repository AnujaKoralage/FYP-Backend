package com.fyp.eholeservice.eholeservice.DTO;

import com.fyp.eholeservice.eholeservice.Enums.TransactionType;

public class EholeTransactionDTO {
    private Long userId;
    private double amount;
    private TransactionType transactionType;

    public EholeTransactionDTO() {
    }

    public EholeTransactionDTO(Long userId, double amount, TransactionType transactionType) {
        this.userId = userId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
