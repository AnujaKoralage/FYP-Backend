package com.fyp.profileservice.profile.Enum;

public enum UserRoleEnum {
    ROLE_TRADER(1, "ROLE_TRADER"),
    ROLE_INVESTOR(2, "ROLE_INVESTOR");

    private Integer value;
    private String label;

    UserRoleEnum(Integer value, String label) {
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
