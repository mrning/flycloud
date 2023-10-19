package com.lqjk.request.req.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */
@Data
public class AuthLogoutRequest {
    @NotEmpty
    private String token;
    @NotEmpty
    private String username;
}
