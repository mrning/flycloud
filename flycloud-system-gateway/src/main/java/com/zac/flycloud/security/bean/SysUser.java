package com.zac.flycloud.security.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 用户表
 */
@Data
@TableName("sys_user")
public class SysUser extends com.zac.flycloud.common.basebeans.BaseEntity {


    /**
     * 用户名
     */
    private String username;

    /**
     * 密码加密后
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱地址
     */
    private String mail;

    /**
     * 电话
     */
    private String phone;

}
