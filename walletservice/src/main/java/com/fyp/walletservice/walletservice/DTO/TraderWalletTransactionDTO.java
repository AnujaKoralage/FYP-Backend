package com.fyp.walletservice.walletservice.DTO;



public class TraderWalletTransactionDTO {

    private Long id;
    private String paymentTransactionId;
    private Double amount;

    @Override
    public String toString() {
        return "TraderWalletTransactionDTO{" +
                "id=" + id +
                ", paymentTransactionId='" + paymentTransactionId + '\'' +
                ", amount=" + amount +
                ", traderWalletDTO=" + traderWalletDTO +
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
}
