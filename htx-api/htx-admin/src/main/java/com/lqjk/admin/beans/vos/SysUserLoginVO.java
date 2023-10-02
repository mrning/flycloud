package com.lqjk.admin.beans.vos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
@Schema(name="登录对象", description="登录对象")
public class SysUserLoginVO {
    @SchemaProperty(name = "账号")
    private String username;
    @SchemaProperty(name = "密码")
    private String password;
    @SchemaProperty(name = "验证码")
    private String captcha;
    @SchemaProperty(name = "验证码key")
    private String checkKey;
}
