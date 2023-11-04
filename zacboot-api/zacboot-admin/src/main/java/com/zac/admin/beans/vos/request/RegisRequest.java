package com.zac.admin.beans.vos.request;

import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
public class RegisRequest {
    @SchemaProperty(name = "手机号")
    private String phone;
    @SchemaProperty(name = "短信验证码")
    private String smscode;
    @SchemaProperty(name = "用户名，如果为空以手机号为用户名")
    private String username;
    @SchemaProperty(name = "密码")
    private String password;
    @SchemaProperty(name = "邮箱，用于找回密码")
    private String mail;
}
