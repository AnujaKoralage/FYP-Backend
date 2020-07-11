package com.fyp.paymentservice.paymentservice.DTO;

import java.util.List;

public class PaybackDTO {

    private List<PaybackTransactionDTO> paybackTransactionDTOS;
    private Integer profitMargin;

    public List<PaybackTransactionDTO> getPaybackTransactionDTOS() {
        return paybackTransactionDTOS;
    }

    public void setPaybackTransactionDTOS(List<PaybackTransactionDTO> paybackTransactionDTOS) {
        this.paybackTransactionDTOS = paybackTransactionDTOS;
    }

    public Integer getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Integer profitMargin) {
        this.profitMargin = profitMargin;
    }
}
