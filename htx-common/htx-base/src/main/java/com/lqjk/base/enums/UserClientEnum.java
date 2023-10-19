package com.lqjk.base.enums;

import org.apache.commons.lang3.StringUtils;

public enum UserClientEnum {
    ADMIN("admin"),
    APP("app");

    private final String value;

    UserClientEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserClientEnum getByValue(String value){
        if (StringUtils.isBlank(value)){
            return null;
        }
        for (UserClientEnum u : UserClientEnum.values()){
            if (value.equals(u.getValue())){
                return u;
            }
        }
        return null;
    }
}
