package com.fyp.eholeservice.eholeservice.DTO;

import com.fyp.eholeservice.eholeservice.Enums.EholeAmountType;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;

import java.time.LocalDateTime;

public class EhDTO {

    private Long id;
    private Long traderId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime completionDate;
    private double totalAmount;
    private double completedAmount;
    private EholeAmountType eholeAmountType;
    private EholeStatusType eholeStatusType;
    private EholeType eholeType;

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(double completedAmount) {
        this.completedAmount = completedAmount;
    }

    public EholeAmountType getEholeAmountType() {
        return eholeAmountType;
    }

    public void setEholeAmountType(EholeAmountType eholeAmountType) {
        this.eholeAmountType = eholeAmountType;
    }

    public EholeStatusType getEholeStatusType() {
        return eholeStatusType;
    }

    public void setEholeStatusType(EholeStatusType eholeStatusType) {
        this.eholeStatusType = eholeStatusType;
    }

    public EholeType getEholeType() {
        return eholeType;
    }

    public void setEholeType(EholeType eholeType) {
        this.eholeType = eholeType;
    }
}
