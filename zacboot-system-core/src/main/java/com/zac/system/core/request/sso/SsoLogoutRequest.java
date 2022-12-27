package com.zac.system.core.request.sso;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SsoLogoutRequest {
    @NotEmpty
    @ApiModelProperty(value = "token",required = true)
    private String token;
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
}
