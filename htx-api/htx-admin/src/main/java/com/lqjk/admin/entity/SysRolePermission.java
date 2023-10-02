package com.lqjk.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
@TableName(value = "sys_role_permission")
public class SysRolePermission extends BaseEntity {

    /**
     * 角色uuid
     */
    @SchemaProperty(name = "角色uuid")
    private String roleUuid;

    /**
     * 权限uuid
     */
    @SchemaProperty(name = "权限uuid")
    private String permissionUuid;
}
