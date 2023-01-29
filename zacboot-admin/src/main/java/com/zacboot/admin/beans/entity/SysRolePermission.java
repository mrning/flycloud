package com.zacboot.admin.beans.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.common.base.basebeans.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "sys_role_permission")
public class SysRolePermission extends BaseEntity {

    /**
     * 角色uuid
     */
    @ApiModelProperty("角色uuid")
    private String roleUuid;

    /**
     * 权限uuid
     */
    @ApiModelProperty("权限uuid")
    private String permissionUuid;
}
