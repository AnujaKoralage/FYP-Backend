package com.fyp.paymentservice.paymentservice.Service;

import com.fyp.paymentservice.paymentservice.DTO.WithdrawDTO;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.credential.CertificateCredential;
import com.paypal.core.credential.ICredential;
import com.paypal.core.credential.SignatureCredential;
import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaypalService {

    @Autowired
    private APIContext apiContext;


    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public PayResponse createPayout(WithdrawDTO withdrawDTO, String email) throws IOException, OAuthException, InvalidResponseDataException, SSLConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        String UserName= "sb-pzhn51531816_api1.business.example.com";
        String Password= "P2QQXG5YDECLHZ7S";
        String Signature= "A6vJo4fshVdP.BScU0f37Yaf0BdFAIQngLhEcTre2pFXkf0hgyJDl3La";
        String AppId = "APP-80W284485P519543T";

        RequestEnvelope env = new RequestEnvelope();
        env.setErrorLanguage("en_US");

        List<Receiver> receiver = new ArrayList<Receiver>();
        Receiver rec = new Receiver();
        rec.setAmount(withdrawDTO.getAmount());
        rec.setEmail(email);
        receiver.add(rec);
        ReceiverList receiverlst = new ReceiverList(receiver);

        PayRequest payRequest = new PayRequest();
        payRequest.setReceiverList(receiverlst);
        payRequest.setRequestEnvelope(env);
        payRequest.setActionType("PAY");
        payRequest.setCurrencyCode("USD");
        payRequest.setCancelUrl("http://localhost:4200/cancel");
        payRequest.setReturnUrl("http://localhost:4200/return");

        Map<String, String> customConfigurationMap = new HashMap<String, String>();
        customConfigurationMap.put("mode", "sandbox");

        AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(customConfigurationMap);
        ICredential iCredential = new SignatureCredential(UserName, Password, Signature);
        ((SignatureCredential) iCredential).setApplicationId(AppId);
        PayResponse payResponse = adaptivePaymentsService.pay(payRequest, iCredential);
        return payResponse;
    }


}
