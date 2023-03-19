package com.zacboot.api.mini.beans.requests;

import lombok.Data;

/**
 * 手机号登录
 */
@Data
public class PhoneLoginRequest {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String captcha;
}
