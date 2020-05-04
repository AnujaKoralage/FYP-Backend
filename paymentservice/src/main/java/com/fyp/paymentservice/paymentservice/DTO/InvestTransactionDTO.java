package com.fyp.paymentservice.paymentservice.DTO;

public class InvestTransactionDTO {

    private Long eholeId;
    private Double amount;

    public Long getEholeId() {
        return eholeId;
    }

    public void setEholeId(Long eholeId) {
        this.eholeId = eholeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
