package com.zacboot.admin.beans.vos.response;

import com.zac.system.core.entity.admin.SysRole;
import com.zac.system.core.entity.admin.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SysUserRoleResponse {

    private String userUuid;
    private String userName;
    private String userRealName;
    private String roleUuid;
    private String roleName;

    public SysUserRoleResponse(SysUser sysUser, SysRole sysRole){
        this.setUserUuid(sysUser.getUuid());
        this.setUserName(sysUser.getUsername());
        this.setUserRealName(sysUser.getRealName());
        this.setRoleUuid(sysRole.getUuid());
        this.setRoleName(sysRole.getRoleName());
    }
}
