package com.fyp.tradeservice.traderservice.Component;

import com.oanda.v20.Context;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gemini.v1.GeminiExchange;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BrokerSpecs {

    public static GeminiExchange geminiExchange;

    private static void geminiExchange() {

        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(GeminiExchange.class);
        exchangeSpecification.setSecretKey("2UNxfhv7rbeAaojNHk6vxnT33CwV");
        exchangeSpecification.setApiKey("account-VN9Z7Gw0XbJoXATvtrmk");
        exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", true);
        exchangeSpecification.setExchangeName("Gemini");

        geminiExchange.applySpecification(exchangeSpecification);
        try {
            geminiExchange.remoteInit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
