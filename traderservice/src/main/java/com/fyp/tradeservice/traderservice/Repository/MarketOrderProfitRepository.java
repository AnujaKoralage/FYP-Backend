package com.fyp.tradeservice.traderservice.Repository;

import com.fyp.tradeservice.traderservice.Entity.MarketOrderProfitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketOrderProfitRepository extends JpaRepository<MarketOrderProfitEntity, Long> {
}
