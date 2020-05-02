package com.fyp.eholeservice.eholeservice.Enums;

public enum EholeAmountType {

    FILLED(0, "FILLED"),
    UNFILLED(1, "UNFILLED");

    private int value;
    private String label;

    EholeAmountType() {
    }

    EholeAmountType(int value, String label) {
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
