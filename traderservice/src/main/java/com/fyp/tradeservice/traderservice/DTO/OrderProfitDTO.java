package com.fyp.tradeservice.traderservice.DTO;

import com.fyp.tradeservice.traderservice.Enum.OrderProfitStatus;

public class OrderProfitDTO {

    private long orderId;
    private double profit;
    private OrderProfitStatus profitStatus;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public OrderProfitStatus getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(OrderProfitStatus profitStatus) {
        this.profitStatus = profitStatus;
    }
}
