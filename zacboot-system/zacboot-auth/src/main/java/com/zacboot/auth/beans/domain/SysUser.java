package com.zacboot.auth.beans.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.core.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户表
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {


    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码加密后
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

}
