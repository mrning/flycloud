package com.zacboot.admin.beans.vos.response;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zacboot.core.entity.admin.SysRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RolePageResponse {

    /**
     * 角色uuid
     */
    private String uuid;

    /**
     * 创建世间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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

    /**
     * 权限uuid
     */
    private List<String> permissions;

    public static RolePageResponse convertByEntity(SysRole sysRole) {
        RolePageResponse response = new RolePageResponse();
        BeanUtil.copyProperties(sysRole, response);
        response.setUuid(sysRole.getUuid());
        response.setCreateTime(sysRole.getCreateTime());
        return response;
    }
}
