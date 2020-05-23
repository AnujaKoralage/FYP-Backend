package com.fyp.tradeservice.traderservice.Enum;

public enum AssetEnum {
    EUR("EUR", 0, 2, true),
    USD("USD", 1, 2, true),
    JPY("JPY", 2, 2, true),
    GBP("GBP", 3, 2, true),
    CHF("CHF", 4, 2, true),
    CAD("CAD", 5, 2, true),
    NZD("NZD", 6, 2, true),
    AUD("AUD", 7, 2, true)
;
    private String assetCode;
    private int assetId;
    private int scale; // asset scale - normally 2 for currencies, 8 for BTC, etc
    private boolean active;

    AssetEnum(String assetCode, int assetId, int scale, boolean active) {
        this.setAssetCode(assetCode);
        this.setAssetId(assetId);
        this.setScale(scale);
        this.setActive(active);
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
