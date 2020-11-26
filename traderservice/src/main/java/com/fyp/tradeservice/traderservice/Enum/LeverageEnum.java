package com.fyp.tradeservice.traderservice.Enum;

public enum LeverageEnum {

    LEV1("1:10", 0, 1000),
    LEV2("1:20", 1, 2000),
    LEV3("1:100", 2, 10000),
    LEV4("1:200", 3, 20000),;
    private String label;
    private int code;
    private long multiplier;

    LeverageEnum(String label, int code, long multiplier) {
        this.label = label;
        this.code = code;
        this.multiplier = multiplier;
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

    public long getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    }
}
