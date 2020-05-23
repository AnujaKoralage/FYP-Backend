package com.example.trader.testtrader;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class TestTrader {

    public static void main(String[] args) throws IOException {

        // Use the factory to get Bitstamp exchange API using default settings
        Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

        // Interested in the public market data feed (no authentication)
        MarketDataService marketDataService = bitstamp.getMarketDataService();

        generic(marketDataService);
        raw((BitstampMarketDataServiceRaw) marketDataService);
    }

    private static void generic(MarketDataService marketDataService) throws IOException {

        Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

        System.out.println(ticker.toString());
    }

    private static void raw(BitstampMarketDataServiceRaw marketDataService) throws IOException {

        BitstampTicker bitstampTicker = marketDataService.getBitstampTicker(CurrencyPair.BTC_USD);

        System.out.println(bitstampTicker.toString());
    }

}
