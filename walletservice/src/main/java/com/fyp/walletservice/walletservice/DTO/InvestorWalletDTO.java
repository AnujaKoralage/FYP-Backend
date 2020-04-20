package com.fyp.walletservice.walletservice.DTO;


import java.time.LocalDate;

public class InvestorWalletDTO {
    private Long id;
    private Long investorId;
    private Double currentBalance;
    private LocalDate createdDate;
    private LocalDate lastUpateedDate;

    @Override
    public String toString() {
        return "InvestorWalletDTO{" +
                "id=" + id +
                ", investorId=" + investorId +
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
}
