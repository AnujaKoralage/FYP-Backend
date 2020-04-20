package com.fyp.walletservice.walletservice.Entity;

import javax.persistence.*;


@Entity
public class InvestorWalletTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    private String paymentTransactionId;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "investorWalletId")
    private
    InvestorWalletEntity investorWalletEntity;

    @Override
    public String toString() {
        return "InvestorWalletTransactionEntity{" +
                "id=" + id +
                ", paymentTransactionId='" + paymentTransactionId + '\'' +
                ", amount=" + amount +
                ", investorWalletEntity=" + investorWalletEntity +
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

    public InvestorWalletEntity getInvestorWalletEntity() {
        return investorWalletEntity;
    }

    public void setInvestorWalletEntity(InvestorWalletEntity investorWalletEntity) {
        this.investorWalletEntity = investorWalletEntity;
    }
}
