package com.fyp.tradeservice.traderservice.Entity;

import com.fyp.tradeservice.traderservice.Enum.AssetEnum;
import com.fyp.tradeservice.traderservice.Enum.OrderAction;
import com.fyp.tradeservice.traderservice.Enum.OrderBookStatusEnum;

import javax.persistence.*;

@Entity
public class OrderBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    long id;
    private OrderAction orderAction;
    private double size;
    private double price;
    private AssetEnum currency;
    private double filledSize;
    private OrderBookStatusEnum orderFillstatus;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private MarketOrderEntity orderId;


    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OrderBookEntity{" +
                "id=" + id +
                ", orderAction=" + orderAction +
                ", size=" + size +
                ", price=" + price +
                ", currency=" + currency +
                ", filledSize=" + filledSize +
                ", orderFillstatus=" + orderFillstatus +
                ", orderId=" + orderId +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public MarketOrderEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(MarketOrderEntity orderId) {
        this.orderId = orderId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AssetEnum getCurrency() {
        return currency;
    }

    public void setCurrency(AssetEnum currency) {
        this.currency = currency;
    }

    public double getFilledSize() {
        return filledSize;
    }

    public void setFilledSize(double filledSize) {
        this.filledSize = filledSize;
    }

    public OrderBookStatusEnum getOrderFillstatus() {
        return orderFillstatus;
    }

    public void setOrderFillstatus(OrderBookStatusEnum orderFillstatus) {
        this.orderFillstatus = orderFillstatus;
    }
}
