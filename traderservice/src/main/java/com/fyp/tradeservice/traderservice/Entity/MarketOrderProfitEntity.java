package com.fyp.tradeservice.traderservice.Entity;

import com.fyp.tradeservice.traderservice.Enum.OrderProfitStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class MarketOrderProfitEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;

    @OneToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private MarketOrderEntity orderEntity;

    private double profit;
    private OrderProfitStatus profitStatus;

    public MarketOrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(MarketOrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderProfitStatus getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(OrderProfitStatus profitStatus) {
        this.profitStatus = profitStatus;
    }
}
