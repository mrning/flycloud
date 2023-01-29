package com.zacboot.admin.beans.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.admin.beans.vos.response.UserPageResponse;
import com.zacboot.common.base.basebeans.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户表
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private static long serialVersionUID = 1L;

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
    private String realName;

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

    public static <T> SysUser convertByRequest(T request) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(request, sysUser);
        return sysUser;
    }

    public UserPageResponse convertToPageRes() {
        UserPageResponse response = new UserPageResponse();
        BeanUtil.copyProperties(this, response);
        response.setUuid(this.getUuid());
        response.setCreateTime(this.getCreateTime());
        return response;
    }

}
