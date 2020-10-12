package com.zac.flycloud.entity.tablemodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zac.flycloud.entity.BaseEntity;
import lombok.Data;
import com.zac.flycloud.annotation.*;
import java.util.Date;


/**
 * 用户表
 */
@Data
public class SysUser extends BaseEntity {


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

}
