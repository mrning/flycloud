package com.zac.admin.beans.vos.request;

import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserAddRequest {

    /**
     * 用户名
     */
    @SchemaProperty(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码加密后
     */
    @SchemaProperty(name = "密码")
    private String password;

    /**
     * 真实姓名
     */
    @SchemaProperty(name = "真实姓名")
    private String realName;

    /**
     * 头像
     */
    @SchemaProperty(name = "头像")
    private String avatar;

    /**
     * 昵称
     */
    @SchemaProperty(name = "昵称")
    private String nickname;

    /**
     * 邮箱地址
     */
    @SchemaProperty(name = "邮箱")
    private String mail;

    /**
     * 电话
     */
    @SchemaProperty(name = "电话")
    private String phone;

    /**
     * 入职时间
     */
    @SchemaProperty(name = "入职时间")
    private Date entryDate;

    /**
     * 上级领导
     */
    @SchemaProperty(name = "上级领导")
    private String parentUserName;

    /**
     * 部门id
     */
    @SchemaProperty(name = "部门id")
    private List<String> deptUuids;

    /**
     * 角色id
     */
    @SchemaProperty(name = "角色id")
    private List<String> roleUuids;
}
