package com.zac.flycloud.bean.vos.response;

import com.zac.flycloud.bean.tablemodel.SysRoleDTO;
import com.zac.flycloud.bean.tablemodel.SysUserDTO;
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

    public void buildByDto(SysUserDTO sysUserDTO, SysRoleDTO sysRoleDTO){
        this.setUserUuid(sysUserDTO.getUuid());
        this.setUserName(sysUserDTO.getUsername());
        this.setUserRealName(sysUserDTO.getRealname());
        this.setRoleUuid(sysRoleDTO.getUuid());
        this.setRoleName(sysRoleDTO.getRoleName());
    }
}
