package com.fyp.walletservice.walletservice.DTO;

import java.time.LocalDate;

public class TraderWalletDTO {

    private Long id;
    private Long traderId;
    private Double currentBalance;
    private LocalDate createdDate;
    private LocalDate lastUpateedDate;

    @Override
    public String toString() {
        return "TraderWalletDTO{" +
                "id=" + id +
                ", traderId=" + traderId +
                ", currentBalance=" + currentBalance +
                ", createdDate=" + createdDate +
                ", lastUpateedDate=" + lastUpateedDate +
                '}';
    }

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
}
