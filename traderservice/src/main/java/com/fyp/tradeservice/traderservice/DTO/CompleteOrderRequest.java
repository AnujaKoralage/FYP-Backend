package com.fyp.tradeservice.traderservice.DTO;

public class CompleteOrderRequest {

    private long orderId;
    private float endPrice;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(float endPrice) {
        this.endPrice = endPrice;
    }
}
