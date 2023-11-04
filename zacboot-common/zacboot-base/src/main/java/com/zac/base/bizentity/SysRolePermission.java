package com.zac.base.bizentity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zac.base.basebeans.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "sys_role_permission")
public class SysRolePermission extends BaseEntity {

    /**
     * 角色uuid
     */
    private String roleUuid;

    /**
     * 权限uuid
     */
    private String permissionUuid;
}
