package com.fyp.tradeservice.traderservice.Enum;

public enum LeverageEnum {

    LEV1("1:10", 0),
    LEV2("1:20", 1),
    LEV3("1:100", 2),
    LEV4("1:200", 3),;
    private String label;
    private int code;

    LeverageEnum(String label, int code) {
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
