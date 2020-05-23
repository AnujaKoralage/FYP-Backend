package com.fyp.tradeservice.traderservice.Repository;

import com.fyp.tradeservice.traderservice.Entity.OrderBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBookEntity, Long> {

    @Query(value = "SELECT * FROM OrderBookEntity c  WHERE  c.orderAction != :orderAction AND c.price = :price AND c.currency = :currency AND c.orderFillstatus = 1", nativeQuery = true)
    public List<OrderBookEntity> requestMatchingOrders(@Param("orderAction") int orderAction,
                                                       @Param("price") double price,
                                                       @Param("currency") int currency);

}
