package com.lqjk.third.beans;

public enum ShTrafficEnum {

    SUCCESS("success","成功"),
    FAILED("500","失败"),
    REPEATED("202", "重复请求"),
    PARAMETER_ERROR("503","参数错误"),
    SIGN_FAILED("505","验签失败"),
    NOT_FIND("404","未找到数据"),
    BUS("BUS","公交"),
    SUBWAY("SUBWAY","地铁"),
    INVALID("0", "无效");

    private String code;
    private String desc;

    ShTrafficEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
