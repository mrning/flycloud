package com.zacboot.common.base.enums;

public enum ModuleEnum {
    ADMIN("admin"),
    APP("app");

    private final String value;

    ModuleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
