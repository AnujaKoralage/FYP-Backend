package com.fyp.walletservice.walletservice.DTO;


import com.fyp.walletservice.walletservice.Enums.MathEnum;

public class TraderWalletTransactionDTO {

    private Long id;
    private String paymentTransactionId;
    private Double amount;
    private MathEnum mathEnum;

    @Override
    public String toString() {
        return "TraderWalletTransactionDTO{" +
                "id=" + getId() +
                ", paymentTransactionId='" + getPaymentTransactionId() + '\'' +
                ", amount=" + getAmount() +
                ", traderWalletDTO=" + getTraderWalletDTO() +
                '}';
    }

    private TraderWalletDTO traderWalletDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TraderWalletDTO getTraderWalletDTO() {
        return traderWalletDTO;
    }

    public void setTraderWalletDTO(TraderWalletDTO traderWalletDTO) {
        this.traderWalletDTO = traderWalletDTO;
    }

    public MathEnum getMathEnum() {
        return mathEnum;
    }

    public void setMathEnum(MathEnum mathEnum) {
        this.mathEnum = mathEnum;
    }
}
