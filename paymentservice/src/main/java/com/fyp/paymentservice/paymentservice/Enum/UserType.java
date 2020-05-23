package com.fyp.paymentservice.paymentservice.Enum;

public enum UserType {
    TRADER(0, "TRADER"),
    INVESTOR(1, "INVESTOR");

    private int value;
    private String label;

    UserType() {
    }

    UserType(int value, String label) {
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
