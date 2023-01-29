package com.zacboot.admin.beans.vos.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisRequest {
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("短信验证码")
    private String smscode;
    @ApiModelProperty("用户名，如果为空以手机号为用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("邮箱，用于找回密码")
    private String mail;
}
