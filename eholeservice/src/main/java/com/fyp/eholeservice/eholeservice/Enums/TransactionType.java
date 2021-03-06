package com.fyp.eholeservice.eholeservice.Enums;

public enum TransactionType {
    INVEST(0, "INVEST"),
    PAYBACK(1, "PAYBACK"),
    FINISH(2, "FINISH")
    ;

    private int value;
    private String label;

    TransactionType() {
    }

    TransactionType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
