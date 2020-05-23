package com.fyp.tradeservice.traderservice.DTO;

import com.fyp.tradeservice.traderservice.Enum.OrderAction;
import com.fyp.tradeservice.traderservice.Enum.OrderType;

public class PlaceorderRequest {

    private double price;
    private long size; // in usd
    private OrderAction orderAction;
    private OrderType orderType;
    private int symbolId;
    private long eholeId;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public int getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(int symbolId) {
        this.symbolId = symbolId;
    }

    public long getEholeId() {
        return eholeId;
    }

    public void setEholeId(long eholeId) {
        this.eholeId = eholeId;
    }
}
