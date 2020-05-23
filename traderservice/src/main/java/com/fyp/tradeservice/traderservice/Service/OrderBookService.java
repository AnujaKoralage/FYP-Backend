package com.fyp.tradeservice.traderservice.Service;

import com.fyp.tradeservice.traderservice.DTO.OrderBookRequest;
import com.fyp.tradeservice.traderservice.Entity.OrderBookEntity;
import com.fyp.tradeservice.traderservice.Enum.OrderBookStatusEnum;
import com.fyp.tradeservice.traderservice.Repository.MarketOrderProfitRepository;
import com.fyp.tradeservice.traderservice.Repository.MarketOrderRepository;
import com.fyp.tradeservice.traderservice.Repository.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBookService {
    @Autowired
    private MarketOrderRepository orderRepository;

    @Autowired
    private MarketOrderProfitRepository profitRepository;

    @Autowired
    private OrderBookRepository orderBookRepository;

    public OrderBookEntity insertOrder(OrderBookRequest orderBookRequest){

        List<OrderBookEntity> orderBookEntities = orderBookRepository.requestMatchingOrders(orderBookRequest.getOrderAction().getCode(), orderBookRequest.getPrice(),
                orderBookRequest.getCurreny().getAssetId());
        System.out.println("LOL   " + orderBookRequest.getOrderAction().getCode());
        System.out.println("LOL   " + orderBookRequest.getPrice());
        System.out.println("LOL   " + orderBookRequest.getCurreny().getAssetId());
        System.out.println(orderBookEntities.size());
        OrderBookEntity orderBookEntity = new OrderBookEntity();
        orderBookEntity.setCurrency(orderBookRequest.getCurreny());
        orderBookEntity.setOrderAction(orderBookRequest.getOrderAction());
        orderBookEntity.setPrice(orderBookRequest.getPrice());
        orderBookEntity.setSize(orderBookRequest.getSize());

        if (orderBookEntities.isEmpty()) {
            orderBookEntity.setFilledSize(0);
            orderBookEntity.setOrderFillstatus(OrderBookStatusEnum.FILLING);
            OrderBookEntity save = orderBookRepository.save(orderBookEntity);
            System.out.println("PURE CRATER");
            return save;
        } else {
            double requiredSize = orderBookRequest.getSize();
            for (OrderBookEntity bookEntity :
                    orderBookEntities) {
                System.out.println("LOL OI " + bookEntity.toString());
                double toBeFilledSize = bookEntity.getSize() - bookEntity.getFilledSize();
                if (requiredSize == toBeFilledSize) {
                    bookEntity.setFilledSize(bookEntity.getSize());
                    bookEntity.setOrderFillstatus(OrderBookStatusEnum.FILLED);

                    orderBookEntity.setOrderFillstatus(OrderBookStatusEnum.FILLED);
                    orderBookEntity.setFilledSize(orderBookEntity.getSize());
                    orderBookRepository.save(bookEntity);
                    System.out.println("EQUAL");
                    return orderBookEntity;

                } else if (requiredSize < toBeFilledSize) {
                    orderBookEntity.setOrderFillstatus(OrderBookStatusEnum.FILLED);
                    orderBookEntity.setFilledSize(orderBookEntity.getSize());

                    bookEntity.setFilledSize(bookEntity.getFilledSize() + requiredSize);
                    orderBookRepository.save(bookEntity);
                    System.out.println("<<<<<<<<<");
                    return orderBookEntity;
                } else if (requiredSize > toBeFilledSize) {
                    bookEntity.setFilledSize(bookEntity.getSize());
                    bookEntity.setOrderFillstatus(OrderBookStatusEnum.FILLED);
                    orderBookRepository.save(bookEntity);
                    System.out.println(">>>>>>>>>>>>");

                    requiredSize = requiredSize - toBeFilledSize;
                }
            }
            orderBookEntity.setOrderFillstatus(OrderBookStatusEnum.FILLING);
            orderBookEntity.setFilledSize(orderBookEntity.getSize()-requiredSize);
            return orderBookEntity;
        }

    }

}
