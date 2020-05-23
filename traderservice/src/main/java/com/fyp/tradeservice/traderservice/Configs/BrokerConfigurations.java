package com.fyp.tradeservice.traderservice.Configs;

import com.oanda.v20.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfigurations {



    @Bean
    public Context getContextOanda() {
        Context ctx = new Context(
                "https://api-fxpractice.oanda.com",
                "038d52bd88bd214459b0f3ffe456a414-dc716c462f1985e244a43a7575f88bff");
        return ctx;
    }

//    @Bean
//    public ExchangeApi exchangeApi(){
//        // simple async events handler
//        SimpleEventsProcessor eventsProcessor = new SimpleEventsProcessor(new IEventsHandler() {
//            @Override
//            public void tradeEvent(TradeEvent tradeEvent) {
//                System.out.println("Trade event: " + tradeEvent);
//            }
//
//            @Override
//            public void reduceEvent(ReduceEvent reduceEvent) {
//                System.out.println("Reduce event: " + reduceEvent);
//            }
//
//            @Override
//            public void rejectEvent(RejectEvent rejectEvent) {
//                System.out.println("Reject event: " + rejectEvent);
//            }
//
//            @Override
//            public void commandResult(ApiCommandResult commandResult) {
//                System.out.println("Command result: " + commandResult);
//            }
//
//            @Override
//            public void orderBook(OrderBook orderBook) {
//                System.out.println("OrderBook event: " + orderBook);
//            }
//        });
//
//// default exchange configuration
//        ExchangeConfiguration conf = ExchangeConfiguration.defaultBuilder().build();
//
//// no serialization
//        Supplier<ISerializationProcessor> serializationProcessorFactory = () -> DummySerializationProcessor.INSTANCE;
//
//// build exchange core
//        ExchangeCore exchangeCore = ExchangeCore.builder()
//                .resultsConsumer(eventsProcessor)
//                .serializationProcessorFactory(serializationProcessorFactory)
//                .exchangeConfiguration(conf)
//                .build();
//
//// start up disruptor threads
//        exchangeCore.startup();
//
//// get exchange API for publishing commands
//        ExchangeApi api = exchangeCore.getApi();
//
//        for (SymbolEnum symbolEnum :
//                SymbolEnum.values()) {
//            CoreSymbolSpecification symbolSpecXbtLtc = CoreSymbolSpecification.builder()
//                    .symbolId(symbolEnum.getSymbolId())
//                    .type(SymbolType.CURRENCY_EXCHANGE_PAIR)
//                    .baseCurrency(AssetEnum.valueOf(symbolEnum.getBaseAsset()).getAssetId())
//                    .quoteCurrency(AssetEnum.valueOf(symbolEnum.getQuoteCurrency()).getAssetId())
//                    .baseScaleK(1_000_000L)
//                    .quoteScaleK(10_000L)
//                    .takerFee(1900L)
//                    .makerFee(700L)
//                    .build();
//
//            api.submitBinaryDataAsync(new BatchAddSymbolsCommand(symbolSpecXbtLtc));
//        }
//
//        api.submitCommandAsync(ApiAdjustUserBalance.builder()
//                .uid(StaticContents.userId)
//                .currency(AssetEnum.USD.getAssetId())
//                .amount(2_000_000_000L)
//                .transactionId(1L)
//                .build());
//
//        return api;
//    }

}
