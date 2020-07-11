package com.fyp.tradeservice.traderservice.Repository;

import com.fyp.tradeservice.traderservice.Entity.MarketOrderEntity;
import com.fyp.tradeservice.traderservice.Entity.MarketOrderProfitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketOrderProfitRepository extends JpaRepository<MarketOrderProfitEntity, Long> {

    public MarketOrderProfitEntity findAllByOrderEntity(MarketOrderEntity entity);

}
