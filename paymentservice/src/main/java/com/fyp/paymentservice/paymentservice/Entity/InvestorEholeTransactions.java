package com.fyp.paymentservice.paymentservice.Entity;

import com.fyp.paymentservice.paymentservice.Utils.StringPrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class InvestorEholeTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ttr")
    @GenericGenerator(
            name = "ttr",
            strategy = "com.fyp.paymentservice.paymentservice.Utils.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ITW_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
    private String id;
    private Double amount;
    private Long investorId;
    private Long eholeId;
    private LocalDateTime createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public Long getEholeId() {
        return eholeId;
    }

    public void setEholeId(Long eholeId) {
        this.eholeId = eholeId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
