package com.fyp.eholeservice.eholeservice.Enums;

public enum EholeType {
    ONEHOUREHOLE(0, "ONEHOUREHOLE"),
    ONEDAYEHOLE(1, "ONEDAYEHOLE"),
    ONEWEEKEHOLE(2, "ONEWEEKEHOLE");

    private int value;
    private String label;

    EholeType() {
    }

    EholeType(int value, String label) {
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

    public static EholeType findByValue(int value) {
        for (EholeType c : EholeType.values()) {
            if (c.value == value) {
                return c;
            }
        }

        return null;
    }

    public static boolean contains(int type) {

        for (EholeType c : EholeType.values()) {
            if (c.value == type) {
                return true;
            }
        }

        return false;
    }

}
