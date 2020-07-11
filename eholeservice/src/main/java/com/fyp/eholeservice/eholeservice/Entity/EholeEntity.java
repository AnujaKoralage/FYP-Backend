package com.fyp.eholeservice.eholeservice.Entity;

import com.fyp.eholeservice.eholeservice.Enums.EholeAmountType;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class EholeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    private Long traderId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime completionDate;
    private double totalAmount;
    private double completedAmount;
    private EholeAmountType eholeAmountType;
    private EholeStatusType eholeStatusType;
    private EholeType eholeType;
    private int profitMargin;

    @OneToMany(mappedBy = "eholeEntity")
    private
    List<EholeTransactionEntity> eholeTransactions;

    @Override
    public String toString() {
        return "EholeEntity{" +
                "id=" + getId() +
                ", traderId=" + getTraderId() +
                ", createdDate=" + getCreatedDate() +
                ", updatedDate=" + getUpdatedDate() +
                ", totalAmount=" + getTotalAmount() +
                ", completedAmount=" + getCompletedAmount() +
                ", eholeAmountType=" + getEholeAmountType() +
                ", eholeStatusType=" + getEholeStatusType() +
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

    public List<EholeTransactionEntity> getEholeTransactions() {
        return eholeTransactions;
    }

    public void setEholeTransactions(List<EholeTransactionEntity> eholeTransactions) {
        this.eholeTransactions = eholeTransactions;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public EholeType getEholeType() {
        return eholeType;
    }

    public void setEholeType(EholeType eholeType) {
        this.eholeType = eholeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EholeEntity that = (EholeEntity) o;
        return Double.compare(that.getTotalAmount(), getTotalAmount()) == 0 &&
                Double.compare(that.getCompletedAmount(), getCompletedAmount()) == 0 &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTraderId(), that.getTraderId()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getUpdatedDate(), that.getUpdatedDate()) &&
                Objects.equals(getCompletionDate(), that.getCompletionDate()) &&
                getEholeAmountType() == that.getEholeAmountType() &&
                getEholeStatusType() == that.getEholeStatusType() &&
                getEholeType() == that.getEholeType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTraderId(), getCreatedDate(), getUpdatedDate(), getCompletionDate(), getTotalAmount(), getCompletedAmount(), getEholeAmountType(), getEholeStatusType(), getEholeType(), getEholeTransactions());
    }

    public int getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(int profitMargin) {
        this.profitMargin = profitMargin;
    }
}
