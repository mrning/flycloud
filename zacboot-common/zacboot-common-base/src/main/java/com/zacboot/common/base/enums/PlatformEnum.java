package com.zacboot.common.base.enums;

public enum PlatformEnum {
    ADMIN("admin"),
    APP("app");

    private final String value;

    PlatformEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
