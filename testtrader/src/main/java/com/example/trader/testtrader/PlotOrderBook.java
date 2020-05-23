package com.example.trader.testtrader;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlotOrderBook {

    public static void main(String[] args) throws IOException {

        // Use the factory to get the version 1 Bitstamp exchange API using default settings
        Exchange bitstampExchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

        // Interested in the public market data feed (no authentication)
        MarketDataService marketDataService = bitstampExchange.getMarketDataService();

        System.out.println("fetching data...");

        // Get the current orderbook
        OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

        System.out.println("received data.");

        System.out.println("plotting...");

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Bitstamp Order Book").xAxisTitle("BTC").yAxisTitle("USD").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        // BIDS
        List<Number> xData = new ArrayList<>();
        List<Number> yData = new ArrayList<>();
        BigDecimal accumulatedBidUnits = new BigDecimal("0");
        for (LimitOrder limitOrder : orderBook.getBids()) {
            if (limitOrder.getLimitPrice().doubleValue() > 10) {
                xData.add(limitOrder.getLimitPrice());
                accumulatedBidUnits = accumulatedBidUnits.add(limitOrder.getRemainingAmount());
                yData.add(accumulatedBidUnits);
            }
        }
        Collections.reverse(xData);
        Collections.reverse(yData);

        // Bids Series
        XYSeries series = chart.addSeries("bids", xData, yData);
        series.setMarker(SeriesMarkers.NONE);

        // ASKS
        xData = new ArrayList<>();
        yData = new ArrayList<>();
        BigDecimal accumulatedAskUnits = new BigDecimal("0");
        for (LimitOrder limitOrder : orderBook.getAsks()) {
            if (limitOrder.getLimitPrice().doubleValue() < 1000) {
                xData.add(limitOrder.getLimitPrice());
                accumulatedAskUnits = accumulatedAskUnits.add(limitOrder.getRemainingAmount());
                yData.add(accumulatedAskUnits);
            }
        }

        // Asks Series
        series = chart.addSeries("asks", xData, yData);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chart).displayChart();

//        double[] xData = new double[] { 0.0, 1.0, 2.0 };
//        double[] yData = new double[] { 2.0, 1.0, 0.0 };
//
//        // Create Chart
//        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
//
//        // Show it
//        new SwingWrapper(chart).displayChart();

    }

}
