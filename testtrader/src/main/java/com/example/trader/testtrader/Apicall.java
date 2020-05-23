package com.example.trader.testtrader;


import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gemini.v1.GeminiExchange;
import org.knowm.xchange.gemini.v1.service.GeminiAccountService;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tech.cassandre.trading.bot.configuration.ExchangeAutoConfiguration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.beans.Encoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class Apicall {

    static String base_url = "https://api.gemini.com";
    static String endpoint = "/v1/order/new";
    static String url = base_url + endpoint;
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA384";


    static String gemini_api_key = "account-VN9Z7Gw0XbJoXATvtrmk";
    static String gemini_api_secret = "b'2UNxfhv7rbeAaojNHk6vxnT33CwV'";

    public static void main(String[] args) {
        try {
            main();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public static void main() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        Timestamp payload_nonce = new Timestamp(System.currentTimeMillis());
//
//        Map<String, String> walletObject = new HashMap<>();




//        walletObject.put("request", "/v1/order/new");
//        walletObject.put("nonce", String.valueOf(payload_nonce));
//        walletObject.put("symbol", "btcusd");
//        walletObject.put("amount", "5");
//        walletObject.put("price", "3633.00");
//        walletObject.put("side", "buy");
//        walletObject.put("type", "exchange limit");
////        walletObject.put("options", "maker-or-cancel");
//
//        String b64 = serialize(walletObject);
////        String b64 = Base64.getEncoder().encodeToString(walletObject.toString().getBytes());
//        System.out.println(b64);
//        SecretKeySpec secretKeySpec = new SecretKeySpec(gemini_api_secret.getBytes(), HMAC_SHA1_ALGORITHM);
//        final Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
//        mac.init(secretKeySpec);
//        byte[] result = mac.doFinal(b64.getBytes());
//        String signature = bytesToHex(result);
////        System.out.println("SIG " + signature);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "text/plain");
//        headers.add("Content-Length", "0");
//        headers.add("X-GEMINI-APIKEY", gemini_api_key);
//        headers.add("X-GEMINI-PAYLOAD", b64);
//        headers.add("X-GEMINI-SIGNATURE", signature);
//        headers.add("Cache-Control", "no-cache");
//
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(null, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//        System.out.println(exchange.getStatusCode());
//        System.out.println(exchange.getBody());
    }
    public static String serialize(Object object) throws IOException {
        ByteArrayOutputStream byteaOut = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = null;
        try {
            gzipOut = new GZIPOutputStream(new Base64OutputStream(byteaOut));
            gzipOut.write(new Gson().toJson(object).getBytes("UTF-8"));
        } finally {
            if (gzipOut != null) try { gzipOut.close(); } catch (IOException logOrIgnore) {}
        }
        return new String(byteaOut.toByteArray());
    }
    private static String bytesToHex(final byte[] hash) {
        final StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            final String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
