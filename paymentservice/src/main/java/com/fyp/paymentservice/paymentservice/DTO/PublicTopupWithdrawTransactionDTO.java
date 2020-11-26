package com.fyp.paymentservice.paymentservice.DTO;

import com.fyp.paymentservice.paymentservice.Enum.PaymentStatusTypes;
import com.fyp.paymentservice.paymentservice.Enum.TransactionTypes;

import java.time.LocalDate;

public class PublicTopupWithdrawTransactionDTO {

    private String paypalPaymentId;
    private Double amount;
    private TransactionTypes transactionType;

    public PublicTopupWithdrawTransactionDTO() {
    }

    public PublicTopupWithdrawTransactionDTO(String paypalPaymentId, Double amount, TransactionTypes transactionType, LocalDate createdDate, PaymentStatusTypes statusType) {
        this.paypalPaymentId = paypalPaymentId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdDate = createdDate;
        this.statusType = statusType;
    }

    private LocalDate createdDate;
    private PaymentStatusTypes statusType;

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public PaymentStatusTypes getStatusType() {
        return statusType;
    }

    public void setStatusType(PaymentStatusTypes statusType) {
        this.statusType = statusType;
    }
}
