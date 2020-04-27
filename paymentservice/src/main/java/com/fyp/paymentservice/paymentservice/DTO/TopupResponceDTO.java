package com.fyp.paymentservice.paymentservice.DTO;

public class TopupResponceDTO {

    private String redirectUrl;
    private String transactionId;

    public TopupResponceDTO() {
    }

    public TopupResponceDTO(String redirectUrl, String transactionId) {
        this.redirectUrl = redirectUrl;
        this.transactionId = transactionId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
