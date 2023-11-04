package com.zac.request.req.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户登录参数
 */
@Data
public class AuthLoginRequest {
    private String userUuid;
    private String username;
    private String password;
}
