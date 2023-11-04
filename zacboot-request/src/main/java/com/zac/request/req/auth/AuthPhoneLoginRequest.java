package com.zac.request.req.auth;

import lombok.Data;


/**
 * 用户手机号登录参数
 */
@Data
public class AuthPhoneLoginRequest {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 客户端类型
     */
    private String clientId;
}
