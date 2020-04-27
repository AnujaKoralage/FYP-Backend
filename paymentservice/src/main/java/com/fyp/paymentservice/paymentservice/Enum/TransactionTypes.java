package com.fyp.paymentservice.paymentservice.Enum;

public enum TransactionTypes {
    TOP_UP(0, "TOP_UP"),
    WITHDROW(1, "WITHDRAW");

    private Integer value;
    private String label;

    TransactionTypes(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
