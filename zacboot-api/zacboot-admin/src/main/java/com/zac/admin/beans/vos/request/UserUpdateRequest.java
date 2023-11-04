package com.zac.admin.beans.vos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserUpdateRequest {

    private String uuid;

    /**
     * 用户名
     */
    @SchemaProperty(name = "用户名")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
