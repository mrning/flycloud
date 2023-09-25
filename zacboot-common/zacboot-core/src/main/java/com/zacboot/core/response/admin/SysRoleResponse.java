package com.zacboot.core.response.admin;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zacboot.core.entity.admin.SysRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SysRoleResponse {


    private String uuid;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 描述
     */
    private String description;

    public static SysRoleResponse convertByEntity(SysRole sysRole) {
        SysRoleResponse sysRoleResponse = new SysRoleResponse();
        BeanUtil.copyProperties(sysRole, sysRoleResponse);
        return sysRoleResponse;
    }
}
