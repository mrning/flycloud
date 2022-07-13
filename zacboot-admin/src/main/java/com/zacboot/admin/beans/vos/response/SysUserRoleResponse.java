package com.zacboot.admin.beans.vos.response;

import com.zacboot.admin.beans.entity.SysRole;
import com.zacboot.admin.beans.entity.SysUser;
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
        this.setUserRealName(sysUser.getRealname());
        this.setRoleUuid(sysRole.getUuid());
        this.setRoleName(sysRole.getRoleName());
    }
}
