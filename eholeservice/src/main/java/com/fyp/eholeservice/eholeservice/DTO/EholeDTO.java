package com.fyp.eholeservice.eholeservice.DTO;

public class EholeDTO {

    private double completedAmount;
    private int eholeType;
    private int profit;

    public double getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(double completedAmount) {
        this.completedAmount = completedAmount;
    }

    public int getEholeType() {
        return eholeType;
    }

    public void setEholeType(int eholeType) {
        this.eholeType = eholeType;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
