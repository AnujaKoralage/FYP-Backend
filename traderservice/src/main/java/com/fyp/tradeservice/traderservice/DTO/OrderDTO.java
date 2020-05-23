package com.fyp.tradeservice.traderservice.DTO;

import com.fyp.tradeservice.traderservice.Enum.*;

public class OrderDTO {

    private
    Long id;
    private SymbolEnum orderSymbol;
    private Long eholeId;
    private Long orderSize;
    private OrderAction orderAction;
    private OrderType orderType;
    private float startPrice;
    private float endPrice;
    private OrderStatus orderStatus;
    private OrderProfitStatus profitStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SymbolEnum getOrderSymbol() {
        return orderSymbol;
    }

    public void setOrderSymbol(SymbolEnum orderSymbol) {
        this.orderSymbol = orderSymbol;
    }

    public Long getEholeId() {
        return eholeId;
    }

    public void setEholeId(Long eholeId) {
        this.eholeId = eholeId;
    }

    public Long getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(Long orderSize) {
        this.orderSize = orderSize;
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

    public float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(float startPrice) {
        this.startPrice = startPrice;
    }

    public float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(float endPrice) {
        this.endPrice = endPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderProfitStatus getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(OrderProfitStatus profitStatus) {
        this.profitStatus = profitStatus;
    }
}
