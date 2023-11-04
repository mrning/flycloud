package com.zac.admin.beans.vos.response;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zac.base.bizentity.SysUser;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysUserResponse {

    /**
     * 用户uuid
     */
    private String uuid;

    /**
     * 创建世间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryDate;

    /**
     * 上级领导
     */
    @SchemaProperty(name = "上级领导")
    private String parentUserName;

    /**
     * 角色id
     */
    @SchemaProperty(name = "角色id")
    private List<String> roleUuids;

    /**
     * 部门id
     */
    @SchemaProperty(name = "部门id")
    private List<String> deptUuids;

    public static SysUserResponse convertByEntity(SysUser sysUser) {
        SysUserResponse response = new SysUserResponse();
        BeanUtil.copyProperties(sysUser, response);
        response.setUuid(sysUser.getUuid());
        response.setCreateTime(sysUser.getCreateTime());
        return response;
    }
}
