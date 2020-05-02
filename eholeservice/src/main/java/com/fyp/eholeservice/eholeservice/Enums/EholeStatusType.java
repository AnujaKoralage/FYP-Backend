package com.fyp.eholeservice.eholeservice.Enums;

public enum EholeStatusType {
    ACTIVE(0, "ACTIVE"),
    FINISHED(1, "FINISHED"),
    CANCELED(2, "CANCELED"),
    TRADING(3, "TRADING");


    private int value;
    private String label;

    EholeStatusType() {
    }

    EholeStatusType(int value, String label) {
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

    public static EholeStatusType findByLabel(String label) {
        for (EholeStatusType c : EholeStatusType.values()) {
            if (c.label.equals(label)) {
                return c;
            }
        }

        return null;
    }

}
