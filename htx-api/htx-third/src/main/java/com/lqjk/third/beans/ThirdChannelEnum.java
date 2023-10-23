package com.lqjk.third.beans;

/**
 * @ClassName ThirdChannelEnum
 * @Description TODO
 * @Author YJD
 * @Date 2023/3/17 8:50
 * @Version 1.0
 */
public enum ThirdChannelEnum {

    HTX_APP("htx","沪碳行APP"),
    T3_TAXI("t3Taxi","T3打车"),
    SHANGHAI_APP("shanghaiApp","上海交通卡APP");

    private final String code;
    private final String desc;

    ThirdChannelEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public static  String getDescByCode(String code){
        for (ThirdChannelEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return code;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
