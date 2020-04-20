package com.fyp.walletservice.walletservice.DTO;



public class InvestorWalletTransactionDTO {

    private Long id;
    private String paymentTransactionId;
    private Double amount;
    private InvestorWalletDTO investorWalletDTO;

    @Override
    public String toString() {
        return "InvestorWalletTransactionDTO{" +
                "id=" + id +
                ", paymentTransactionId='" + paymentTransactionId + '\'' +
                ", amount=" + amount +
                ", investorWalletDTO=" + investorWalletDTO +
                '}';
    }

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

    public InvestorWalletDTO getInvestorWalletDTO() {
        return investorWalletDTO;
    }

    public void setInvestorWalletDTO(InvestorWalletDTO investorWalletDTO) {
        this.investorWalletDTO = investorWalletDTO;
    }
}
