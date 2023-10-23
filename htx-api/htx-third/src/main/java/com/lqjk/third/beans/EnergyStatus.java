package com.lqjk.third.beans;

public enum EnergyStatus {

    COLLECTED("已收取", "01"),
    NOT_COLLECTED("未收取","00");

    private String status;
    private String code;

    EnergyStatus(String status, String code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
