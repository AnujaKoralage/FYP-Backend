package com.fyp.walletservice.walletservice.Entity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class TraderWalletEntity {
    @Override
    public String toString() {
        return "TraderWalletEntity{" +
                "id=" + getId() +
                ", traderId=" + getTraderId() +
                ", currentBalance=" + getCurrentBalance() +
                ", createdDate=" + getCreatedDate() +
                ", lastUpateedDate=" + getLastUpateedDate() +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    private Long traderId;
    private Double currentBalance;
    private LocalDate createdDate;
    private LocalDate lastUpateedDate;

    @OneToMany(mappedBy = "traderWalletEntity")
    private
    List<TraderWalletTransactionsEntity> traderWalletTransactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
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

    public List<TraderWalletTransactionsEntity> getTraderWalletTransactions() {
        return traderWalletTransactions;
    }

    public void setTraderWalletTransactions(List<TraderWalletTransactionsEntity> traderWalletTransactions) {
        this.traderWalletTransactions = traderWalletTransactions;
    }
}
