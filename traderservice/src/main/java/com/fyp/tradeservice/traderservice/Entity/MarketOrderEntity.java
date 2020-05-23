package com.fyp.tradeservice.traderservice.Entity;

import com.fyp.tradeservice.traderservice.Enum.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class MarketOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    private SymbolEnum orderSymbol;
    private Long eholeId;
    private Long traderId;
    private Long orderSize;
    private OrderAction orderAction;
    private OrderType orderType;
    private double startPrice;
    private double endPrice;
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "orderEntity")
    private
    MarketOrderProfitEntity marketOrderProfitEntity;

    @OneToMany(mappedBy = "orderId")
    private
    List<OrderBookEntity> orderBookEntities;

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

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public MarketOrderProfitEntity getMarketOrderProfitEntity() {
        return marketOrderProfitEntity;
    }

    public void setMarketOrderProfitEntity(MarketOrderProfitEntity marketOrderProfitEntity) {
        this.marketOrderProfitEntity = marketOrderProfitEntity;
    }

    public List<OrderBookEntity> getOrderBookEntities() {
        return orderBookEntities;
    }

    public void setOrderBookEntities(List<OrderBookEntity> orderBookEntities) {
        this.orderBookEntities = orderBookEntities;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }
}
