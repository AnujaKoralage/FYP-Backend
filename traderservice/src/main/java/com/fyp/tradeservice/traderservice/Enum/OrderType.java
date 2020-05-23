package com.fyp.tradeservice.traderservice.Enum;

public enum OrderType {
    MARKET("MARKET", 0),
    LIMIT("LIMIT", 1);
    private String label;
    private int id;

    OrderType(String label, int id) {
        this.label = label;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
