package com.fyp.paymentservice.paymentservice.Entity;

import com.fyp.paymentservice.paymentservice.Enum.PaymentStatusTypes;
import com.fyp.paymentservice.paymentservice.Enum.TransactionTypes;
import com.fyp.paymentservice.paymentservice.Utils.Identifiable;
import com.fyp.paymentservice.paymentservice.Utils.StringPrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TraderTopupWithdrawTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ttr")
    @GenericGenerator(
            name = "ttr",
            strategy = "com.fyp.paymentservice.paymentservice.Utils.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TTW_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
    private String id;
    private String paypalPaymentId;
    private Long traderId;
    private Double amount;
    private TransactionTypes transactionType;
    private LocalDate createdDate;
    private PaymentStatusTypes statusType;

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public PaymentStatusTypes getStatusType() {
        return statusType;
    }

    public void setStatusType(PaymentStatusTypes statusType) {
        this.statusType = statusType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
