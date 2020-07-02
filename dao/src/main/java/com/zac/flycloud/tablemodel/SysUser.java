package com.zac.flycloud.tablemodel;

import com.zac.flycloud.actable.annotation.AutoColumn;
import lombok.Data;

import java.util.Date;


/**
 * 用户表
 */
@Data
public class SysUser {

    private Long id;

    /**
     * 用户名
     */
    @AutoColumn(isNull = false)
    private String username;

    /**
     * 密码加密后
     */
    @AutoColumn(isNull = false)
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

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 启用状态
     */
    @AutoColumn(defaultValue = "1")
    private Boolean enableStatus;
}
