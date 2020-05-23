package com.example.trader.testtrader;

import com.google.gson.Gson;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.order.*;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.*;
import exchange.core2.core.ExchangeApi;
import exchange.core2.core.ExchangeCore;
import exchange.core2.core.IEventsHandler;
import exchange.core2.core.SimpleEventsProcessor;
import exchange.core2.core.common.api.ApiAddUser;
import exchange.core2.core.common.api.ApiAdjustUserBalance;
import exchange.core2.core.common.cmd.CommandResultCode;
import exchange.core2.core.common.config.ExchangeConfiguration;
import exchange.core2.core.processors.journaling.DummySerializationProcessor;
import exchange.core2.core.processors.journaling.ISerializationProcessor;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampUtils;
import org.knowm.xchange.bitstamp.dto.account.*;
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gemini.v1.GeminiExchange;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiLimitOrder;
import org.knowm.xchange.gemini.v1.service.GeminiAccountServiceRaw;
import org.knowm.xchange.gemini.v1.service.GeminiBaseService;
import org.knowm.xchange.gemini.v1.service.GeminiTradeService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
//import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class AccountInfo {

    public static void main(String[] args) throws IOException {

//        String accountId = "101-004-14773509-001";

//        Exchange bitstamp = BitstampDemoUtils.createExchange();
//        AccountService accountService = bitstamp.getAccountService();
//        TradeService tradeService = bitstamp.getTradeService();
//        System.out.println(bitstamp.getExchangeSpecification().getSslUri());
//
////        generic(accountService);
//        raw((GeminiBaseService) accountService);

//        GeminiExchange geminiExchange = new GeminiExchange();
//        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(GeminiExchange.class);
//        exchangeSpecification.setSecretKey("2UNxfhv7rbeAaojNHk6vxnT33CwV");
//        exchangeSpecification.setApiKey("account-VN9Z7Gw0XbJoXATvtrmk");
//        exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", true);
//        exchangeSpecification.setExchangeName("Gemini");
//
//        geminiExchange.applySpecification(exchangeSpecification);
//        geminiExchange.remoteInit();
//        AccountService accountService = geminiExchange.getAccountService();
//        TradeService tradeService = geminiExchange.getTradeService();
//        GeminiTradeService.GeminiTradeHistoryParams geminiTradeHistoryParams = new GeminiTradeService.GeminiTradeHistoryParams();
////        geminiTradeHistoryParams.setCurrencyPair(CurrencyPair.BTC_USD);
////        geminiTradeHistoryParams.setLimit(5);
////        geminiTradeHistoryParams.setStartTime(Date.from(Instant.now()));
////        UserTrades tradeHistory = tradeService.getTradeHistory(geminiTradeHistoryParams);
////        System.out.println(tradeHistory.getUserTrades());
////        MarketDataService marketDataService = geminiExchange.getMarketDataService();
////        OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
////        System.out.println(orderBook.getOrders(Order.OrderType.ASK));

//        GeminiLimitOrder geminiLimitOrder = new GeminiLimitOrder(Order.OrderType.BID, BigDecimal.valueOf(1), CurrencyPair.BTC_USD, "101", new Date(), BigDecimal.valueOf(1));
//        String s = tradeService.placeLimitOrder(geminiLimitOrder);
//        System.out.println(s);
       /* TradeHistoryParams tradeHistoryParams = tradeService.createTradeHistoryParams();

        UserTrades tradeHistory = tradeService.getTradeHistory(tradeHistoryParams);
        List<UserTrade> userTrades = tradeHistory.getUserTrades();
        List<Trade> trades = tradeHistory.getTrades();
        System.out.println(trades);*/

//        Context ctx = new Context(
//                "https://api-fxpractice.oanda.com",
//                "038d52bd88bd214459b0f3ffe456a414-dc716c462f1985e244a43a7575f88bff");
//
//        try {
////            AccountSummary summary = ctx.account.summary(
////                    new AccountID(accountId)).getAccount();
////
//            AccountID accountID = new AccountID(accountId);
//            List<Order> orders = ctx.order.list(accountID).getOrders();
//            for (Order order :
//                    orders) {
//                System.out.println(order.getId() + "  " + order.getState());
//            }
//            System.out.println("---------------- ----------------------- ------------------- ---------------");
//            TradeID tradeID = new TradeID("7");
//            TradeSpecifier tradeSpecifier = new TradeSpecifier(tradeID);
//            TradeGetResponse tradeGetResponse = ctx.trade.get(accountID, tradeSpecifier);
//            System.out.println(tradeGetResponse.getTrade().toString());

//            System.out.println("---------------- ----------------------- ------------------- ---------------");
//
//            OrderSpecifier orderSpecifier = new OrderSpecifier(new OrderID("6"));
//            OrderGetResponse orderGetResponse = ctx.order.get(accountID, orderSpecifier);
//            System.out.println(orderGetResponse.getOrder().getType());

//            OrderListRequest orderListRequest = new OrderListRequest(accountID);
//            orderListRequest.setState(OrderStateFilter.ALL);
//            OrderListResponse list = ctx.order.list(orderListRequest);
//            System.out.println("FUNCKING COUNT " + list.getOrders().size());
//            for (Order order:
//                    list.getOrders()) {
//                System.out.println(order.getId());
//            }
//
//            TradeListRequest tradeListRequest = new TradeListRequest(accountID);
//            tradeListRequest.setState(TradeStateFilter.ALL);
//            TradeListResponse list1 = ctx.trade.list(tradeListRequest);
//            System.out.println("FUNCKING COUNT " + list1.getTrades().size());
//            for (Trade trade:
//                 list1.getTrades()) {
//                System.out.println(trade.getId());
//            }

//            OrderCreateRequest orderCreateRequest = new OrderCreateRequest(accountID);
//            LimitOrderRequest limitOrderRequest = new LimitOrderRequest();
//            limitOrderRequest.setUnits(10);
//            limitOrderRequest.setInstrument("EUR_USD");
//            limitOrderRequest.setTimeInForce(TimeInForce.FOK);
//            limitOrderRequest.setType(OrderType.MARKET);
//            orderCreateRequest.setOrder(limitOrderRequest);
////
//            OrderCreateResponse orderCreateResponse = ctx.order.create(orderCreateRequest);
//            System.out.println(orderCreateResponse.getLastTransactionID());

//            OrderGetResponse orderGetResponse = ctx.order.get(accountID, new OrderSpecifier(orderCreateResponse.getLastTransactionID()));
//            Gson gson = new Gson();
//            String s = gson.toJson(orderGetResponse.getOrder());
//            MarketOrder order = (MarketOrder) orderGetResponse.getOrder();
//            System.out.println(order.getPriceBound());

//            TradeGetResponse tradeGetResponse = ctx.trade.get(accountID, new TradeSpecifier(orderCreateResponse.getLastTransactionID()));
//            System.out.println(tradeGetResponse.getTrade().toString());


//        } catch (Exception e) {
//            e.printStackTrace();
//        }

// simple async events handler
        SimpleEventsProcessor eventsProcessor = new SimpleEventsProcessor(new IEventsHandler() {
            @Override
            public void tradeEvent(TradeEvent tradeEvent) {
                System.out.println("Trade event: " + tradeEvent);
            }

            @Override
            public void reduceEvent(ReduceEvent reduceEvent) {
                System.out.println("Reduce event: " + reduceEvent);
            }

            @Override
            public void rejectEvent(RejectEvent rejectEvent) {
                System.out.println("Reject event: " + rejectEvent);
            }

            @Override
            public void commandResult(ApiCommandResult commandResult) {
                System.out.println("Command result: " + commandResult);
            }

            @Override
            public void orderBook(OrderBook orderBook) {
                System.out.println("OrderBook event: " + orderBook);
            }
        });

// default exchange configuration
        ExchangeConfiguration conf = ExchangeConfiguration.defaultBuilder().build();

// no serialization
        Supplier<ISerializationProcessor> serializationProcessorFactory = () -> DummySerializationProcessor.INSTANCE;

// build exchange core
        ExchangeCore exchangeCore = ExchangeCore.builder()
                .resultsConsumer(eventsProcessor)
                .serializationProcessorFactory(serializationProcessorFactory)
                .exchangeConfiguration(conf)
                .build();

// start up disruptor threads
        exchangeCore.startup();

// get exchange API for publishing commands
        ExchangeApi api = exchangeCore.getApi();

        // create user uid=301
        CompletableFuture<CommandResultCode> commandResultCodeCompletableFuture = api.submitCommandAsync(ApiAddUser.builder()
                .uid(301L)
                .build());
        try {
            System.out.println(commandResultCodeCompletableFuture.get().getCode());

            CompletableFuture<CommandResultCode> commandResultCodeCompletableFuture1 = api.submitCommandAsync(ApiAdjustUserBalance.builder()
                    .uid(301L)
                    .currency(2)
                    .amount(2_000_000_000L)
                    .transactionId(1L)
                    .build());

            System.out.println(commandResultCodeCompletableFuture1.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    private static void generic(AccountService accountService) throws IOException {

        // Get the account information
        org.knowm.xchange.dto.account.AccountInfo accountInfo = accountService.getAccountInfo();
        System.out.println("AccountInfo as String: " + accountInfo.toString());

        String depositAddress = accountService.requestDepositAddress(Currency.BTC);
        System.out.println("Deposit address: " + depositAddress);

        String withdrawResult = accountService.withdrawFunds(Currency.BTC, new BigDecimal(1).movePointLeft(4), "XXX");
        System.out.println("withdrawResult = " + withdrawResult);
    }

    private static void raw(GeminiBaseService accountService) throws IOException {

        // Get the account information
//        GeminiBalancesResponse[] bitstampBalance = accountService.getGeminiAccountInfo();
//        System.out.println("ARRAY  "+bitstampBalance);
//        System.out.println("BitstampBalance: " + bitstampBalance[0].getAmount() + "  " + bitstampBalance[0].getAvailable());
//
//        GeminiDepositAddressResponse depositAddress = accountService.requestDepositAddressRaw(Currency.USD);
//        System.out.println("BitstampDepositAddress address: " + depositAddress);

//        final List<DepositTransaction> unconfirmedDeposits = accountService.getUnconfirmedDeposits();
//        System.out.println("Unconfirmed deposits:");
//        for (DepositTransaction unconfirmedDeposit : unconfirmedDeposits) {
//            System.out.println(unconfirmedDeposit);
//        }
//
//        final List<WithdrawalRequest> withdrawalRequests = accountService.getWithdrawalRequests((long) 2);
//        System.out.println("Withdrawal requests:");
//        for (WithdrawalRequest unconfirmedDeposit : withdrawalRequests) {
//            System.out.println(unconfirmedDeposit);
//        }

//        String withdrawResult = accountService.withdraw(Currency.USD, BigDecimal.valueOf(10), "one huttakta yapan mata pakada");
//        System.out.println("BitstampBooleanResponse = " + withdrawResult);
    }

}
