package com.fyp.tradeservice.traderservice.Repository;

import com.fyp.tradeservice.traderservice.Entity.MarketOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketOrderRepository extends JpaRepository<MarketOrderEntity, Long> {

    public MarketOrderEntity findById(long id);

    @Query(value = "SELECT SUM(m.orderSize) FROM MarketOrderEntity m where m.eholeId = eholeId", nativeQuery = true)
    public Double totalEholeCost(@Param("eholeId") long eholeId);

    public List<MarketOrderEntity> findAllByEholeId(long id);

    public List<MarketOrderEntity> findAllByTraderId(long id);


}
