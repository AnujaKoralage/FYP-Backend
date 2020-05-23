package com.fyp.tradeservice.traderservice.DTO;

import com.fyp.tradeservice.traderservice.Enum.AssetEnum;
import com.fyp.tradeservice.traderservice.Enum.OrderAction;

public class OrderBookRequest {

    private AssetEnum curreny;
    private double price;
    private OrderAction orderAction;
    private double size;


    public AssetEnum getCurreny() {
        return curreny;
    }

    public void setCurreny(AssetEnum curreny) {
        this.curreny = curreny;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
