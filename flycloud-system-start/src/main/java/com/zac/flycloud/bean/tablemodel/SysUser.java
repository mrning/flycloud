package com.zac.flycloud.bean.tablemodel;

import com.zac.flycloud.bean.basebean.BaseEntity;
import com.zac.flycloud.bean.annotation.AutoColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户表
 */
@Data
public class SysUser extends BaseEntity {


    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @AutoColumn(isNull = false)
    private String username;

    /**
     * 密码加密后
     */
    @ApiModelProperty(value = "密码")
    @AutoColumn(isNull = false)
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
