package com.fyp.walletservice.walletservice.Entity;

import javax.persistence.*;


@Entity
public class TraderWalletTransactionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    private String paymentTransactionId;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "trderWalletId")
    private
    TraderWalletEntity traderWalletEntity;

    @Override
    public String toString() {
        return "TraderWalletTransactionsEntity{" +
                "id=" + id +
                ", paymentTransactionId='" + paymentTransactionId + '\'' +
                ", amount=" + amount +
                ", traderWalletEntity=" + traderWalletEntity +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TraderWalletEntity getTraderWalletEntity() {
        return traderWalletEntity;
    }

    public void setTraderWalletEntity(TraderWalletEntity traderWalletEntity) {
        this.traderWalletEntity = traderWalletEntity;
    }
}
