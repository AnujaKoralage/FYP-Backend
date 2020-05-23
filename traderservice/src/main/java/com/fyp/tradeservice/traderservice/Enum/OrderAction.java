package com.fyp.tradeservice.traderservice.Enum;

public enum OrderAction {

    BUY("BUY", 0),
    SELL("SELL", 1),;
    private String label;
    private int code;

    OrderAction(String label, int code) {
        this.label = label;
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
