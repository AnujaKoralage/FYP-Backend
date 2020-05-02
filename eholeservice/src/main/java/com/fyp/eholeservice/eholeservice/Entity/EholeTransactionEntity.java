package com.fyp.eholeservice.eholeservice.Entity;

import com.fyp.eholeservice.eholeservice.Enums.TransactionType;
import com.fyp.eholeservice.eholeservice.Enums.UserType;

import javax.persistence.*;

@Entity
public class EholeTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    private Long userId;
    private UserType userType;
    private double amount;
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "eholeId")
    private
    EholeEntity eholeEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public EholeEntity getEholeEntity() {
        return eholeEntity;
    }

    public void setEholeEntity(EholeEntity eholeEntity) {
        this.eholeEntity = eholeEntity;
    }
}
