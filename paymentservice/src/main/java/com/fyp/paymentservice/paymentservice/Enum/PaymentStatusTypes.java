package com.fyp.paymentservice.paymentservice.Enum;

public enum PaymentStatusTypes {
    PROCESSING(0, "PROCESSING"),
    SUCCESS(1, "SUCCESS"),
    FAIL(2, "FAIL");


    private Integer value;
    private String label;

    PaymentStatusTypes(Integer value, String label) {
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
