package com.example.trader.testtrader;

import ch.algotrader.entity.trade.OrderVO;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.gemini.v1.GeminiExchange;

public class BitstampDemoUtils {

    public static Exchange createExchange() {

//        ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
        ExchangeSpecification defaultExchangeSpecification = new GeminiExchange().getDefaultExchangeSpecification();
        defaultExchangeSpecification.setUserName("anujakoralage@gmail.com");
        defaultExchangeSpecification.setApiKey("VN9Z7Gw0XbJoXATvtrmk");
        defaultExchangeSpecification.setSslUri("https://api.sandbox.gemini.com");
        defaultExchangeSpecification.setSecretKey("2UNxfhv7rbeAaojNHk6vxnT33CwV");
        return ExchangeFactory.INSTANCE.createExchange(defaultExchangeSpecification);
    }

}
