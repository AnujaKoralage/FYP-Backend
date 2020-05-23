package com.fyp.tradeservice.traderservice.Service;

import com.fyp.tradeservice.traderservice.DTO.OrderBookRequest;
import com.fyp.tradeservice.traderservice.DTO.OrderDTO;
import com.fyp.tradeservice.traderservice.DTO.PlaceorderRequest;
import com.fyp.tradeservice.traderservice.Entity.MarketOrderEntity;
import com.fyp.tradeservice.traderservice.Entity.MarketOrderProfitEntity;
import com.fyp.tradeservice.traderservice.Entity.OrderBookEntity;
import com.fyp.tradeservice.traderservice.Enum.*;
import com.fyp.tradeservice.traderservice.Repository.MarketOrderProfitRepository;
import com.fyp.tradeservice.traderservice.Repository.MarketOrderRepository;
import com.fyp.tradeservice.traderservice.Repository.OrderBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    MarketOrderRepository orderRepository;

    @Autowired
    MarketOrderProfitRepository profitRepository;

    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    OrderBookService orderBookService;

    @Autowired
    ModelMapper modelMapper;

    public OrderDTO placeOrder(PlaceorderRequest placeorderRequest, long traderId) throws Exception {

        MarketOrderEntity marketOrderEntity = new MarketOrderEntity();
        marketOrderEntity.setEholeId(placeorderRequest.getEholeId());
        marketOrderEntity.setOrderAction(placeorderRequest.getOrderAction());
        marketOrderEntity.setOrderSize(placeorderRequest.getSize());
        marketOrderEntity.setOrderStatus(OrderStatus.ACTIVE);
        marketOrderEntity.setOrderSymbol(SymbolEnum.getSymbolById(placeorderRequest.getSymbolId()));
        marketOrderEntity.setOrderType(placeorderRequest.getOrderType());
        marketOrderEntity.setStartPrice(placeorderRequest.getPrice());
        marketOrderEntity.setTraderId(traderId);

        OrderBookRequest orderBookRequest = new OrderBookRequest();
        orderBookRequest.setPrice(placeorderRequest.getPrice());
        double baseCurrencyBuyingQuantity = placeorderRequest.getSize() / placeorderRequest.getPrice();

        if (placeorderRequest.getOrderAction().equals(OrderAction.BUY)) {

            orderBookRequest.setOrderAction(OrderAction.BUY);
            orderBookRequest.setCurreny(AssetEnum.valueOf(SymbolEnum.getSymbolById(placeorderRequest.getSymbolId()).getBaseAsset()));
            orderBookRequest.setSize(baseCurrencyBuyingQuantity);
        } else {

            orderBookRequest.setOrderAction(OrderAction.SELL);
            orderBookRequest.setCurreny(AssetEnum.valueOf(SymbolEnum.getSymbolById(placeorderRequest.getSymbolId()).getBaseAsset()));
            orderBookRequest.setSize(baseCurrencyBuyingQuantity);
        }
        OrderBookEntity bookEntity = orderBookService.insertOrder(orderBookRequest);
        MarketOrderEntity save = orderRepository.save(marketOrderEntity);
        bookEntity.setOrderId(save);
        OrderBookEntity save1 = orderBookRepository.save(bookEntity);

        if (save == null || save1 == null) {
            throw new Exception();
        } else {
        ArrayList<OrderBookEntity> orderBookEntities = new ArrayList<>();
        orderBookEntities.add(bookEntity);
        marketOrderEntity.setOrderBookEntities(orderBookEntities);
            OrderDTO map = modelMapper.map(save, OrderDTO.class);
            return map;
        }
    }

    public OrderDTO endOrder(long orderId, double endPrice) {

        MarketOrderEntity orderById = orderRepository.findById(orderId);
        orderById.setEndPrice(endPrice);
        orderById.setOrderStatus(OrderStatus.COMPLETED);

        MarketOrderProfitEntity marketOrderProfitEntity = new MarketOrderProfitEntity();
        marketOrderProfitEntity.setOrderEntity(orderById);
        if (orderById.getOrderAction().equals(OrderAction.BUY)) {
            double baceCurBuyUnits = orderById.getOrderSize() / orderById.getStartPrice();

            double baceCurSellUnits = baceCurBuyUnits * orderById.getEndPrice();

            double profitQuantity = baceCurSellUnits - orderById.getOrderSize();
            setProfitValues(marketOrderProfitEntity, profitQuantity);
        } else {
            double selBaceCurQty = orderById.getOrderSize() / orderById.getStartPrice();

            double sellQuoCurQty = orderById.getOrderSize() / orderById.getEndPrice();
            double profitInBaceCur = sellQuoCurQty - selBaceCurQty;
            double realProfit = profitInBaceCur / orderById.getEndPrice();
            setProfitValues(marketOrderProfitEntity, realProfit);
        }
        MarketOrderEntity save = orderRepository.save(orderById);
        OrderDTO map = modelMapper.map(save, OrderDTO.class);
        map.setProfitStatus(marketOrderProfitEntity.getProfitStatus());
        return map;

    }

    private void setProfitValues(MarketOrderProfitEntity marketOrderProfitEntity, double realProfit) {
        if (realProfit > 0) {
            marketOrderProfitEntity.setProfit(realProfit);
            marketOrderProfitEntity.setProfitStatus(OrderProfitStatus.WIN);
            profitRepository.save(marketOrderProfitEntity);
        } else if (realProfit < 0) {
            marketOrderProfitEntity.setProfit(realProfit);
            marketOrderProfitEntity.setProfitStatus(OrderProfitStatus.LOSS);
            profitRepository.save(marketOrderProfitEntity);
        } else {
            marketOrderProfitEntity.setProfit(realProfit);
            marketOrderProfitEntity.setProfitStatus(OrderProfitStatus.NONE);
            profitRepository.save(marketOrderProfitEntity);
        }
    }

    public OrderDTO getOrderById(long id) {
        MarketOrderEntity byId = orderRepository.findById(id);
        OrderDTO map = modelMapper.map(byId, OrderDTO.class);
        return map;
    }

    public double getEholeInvestedAmount(long id) {
        if (orderRepository.totalEholeCost(id) == null) {
            return 0;
        }
        return orderRepository.totalEholeCost(id);
    }

    public List<OrderDTO> getOrderByTrader(long id) {
        List<MarketOrderEntity> allByTraderId = orderRepository.findAllByTraderId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (MarketOrderEntity entity :
                allByTraderId) {
            orderDTOList.add(modelMapper.map(entity, OrderDTO.class));
        }
        return orderDTOList;
    }

    public List<OrderDTO> getOrderbyEhole(long id) {
        List<MarketOrderEntity> allByEholeId = orderRepository.findAllByEholeId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (MarketOrderEntity entity :
                allByEholeId) {
            orderDTOList.add(modelMapper.map(entity, OrderDTO.class));
        }
        return orderDTOList;
    }

}
