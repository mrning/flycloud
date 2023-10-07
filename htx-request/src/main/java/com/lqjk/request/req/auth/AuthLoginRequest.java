package com.lqjk.request.req.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户登录参数
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthLoginRequest {
    @NotEmpty
    private String userUuid;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    /**
     * 客户端类型
     */
    private String clientId;
}
