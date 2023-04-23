package com.zacboot.admin.beans.vos.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserUpdateRequest {

    private String uuid;

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

    /**
     * 入职时间
     */
    @ApiModelProperty(value = "入职时间")
    private Date entryDate;

    /**
     * 上级领导
     */
    @ApiModelProperty(value = "上级领导")
    private String parentUserName;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private List<String> deptUuids;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private List<String> roleUuids;
}
