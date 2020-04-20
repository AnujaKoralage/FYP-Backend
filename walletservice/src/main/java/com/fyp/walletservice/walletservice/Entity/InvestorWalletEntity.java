package com.fyp.walletservice.walletservice.Entity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class InvestorWalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;

    @Override
    public String toString() {
        return "InvestorWalletEntity{" +
                "id=" + getId() +
                ", investorId=" + getInvestorId() +
                ", currentBalance=" + getCurrentBalance() +
                ", createdDate=" + getCreatedDate() +
                ", lastUpateedDate=" + getLastUpateedDate() +
                '}';
    }

    private Long investorId;
    private Double currentBalance;
    private LocalDate createdDate;
    private LocalDate lastUpateedDate;
    @OneToMany(mappedBy = "investorWalletEntity")
    private
    List<InvestorWalletTransactionEntity> investorWalletTransactions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastUpateedDate() {
        return lastUpateedDate;
    }

    public void setLastUpateedDate(LocalDate lastUpateedDate) {
        this.lastUpateedDate = lastUpateedDate;
    }

    public List<InvestorWalletTransactionEntity> getInvestorWalletTransactions() {
        return investorWalletTransactions;
    }

    public void setInvestorWalletTransactions(List<InvestorWalletTransactionEntity> investorWalletTransactions) {
        this.investorWalletTransactions = investorWalletTransactions;
    }
}
