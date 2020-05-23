package com.fyp.tradeservice.traderservice.Enum;

public enum SymbolEnum {
    EURUSD(0,
            "EURUSD",
            AssetEnum.EUR.getAssetCode(),
            AssetEnum.USD.getAssetCode()
            );

    private int symbolId;
    private String symbolCode;

    private String baseAsset;     // base asset
    private String quoteCurrency; // quote/counter currency (OR futures contract currency)

    SymbolEnum(int symbolId, String symbolCode, String baseAsset, String quoteCurrency) {
        this.setSymbolId(symbolId);
        this.setSymbolCode(symbolCode);
        this.setBaseAsset(baseAsset);
        this.setQuoteCurrency(quoteCurrency);
    }

    public int getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(int symbolId) {
        this.symbolId = symbolId;
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public static SymbolEnum getSymbolById(int id){
        for (SymbolEnum symbolEnum :
                SymbolEnum.values()) {
            if (symbolEnum.getSymbolId() == id){
                return symbolEnum;
            }
        }
        return null;
    }
}
