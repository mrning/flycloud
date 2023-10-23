package com.lqjk.third.beans;

/**
 * @Description 能量类型
 * @Author HZY
 * @Date 2021/6/1 9:54
 * @Version 1.0
 **/

public enum EnergyType {

    CARBON("碳减排", "01"),
    CALORIE("精力值", "02"),
    SHANG_HAI_VOUCHER("上海app优惠券","03");

    private String typeName;
    private String code;

    EnergyType(String typeName, String code) {
        this.typeName = typeName;
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static EnergyType getByCode(String code) {
        for (EnergyType codes : values()) {
            if (codes.getCode().equals(code)) {
                return codes;
            }
        }
        return null;
    }
}