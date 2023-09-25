package com.zacboot.core.response.admin;

import lombok.Data;

import java.util.List;

@Data
public class SysUserDeptAndRoleInfo {

    private String userUuid;

    private String userName;

    private List<SysDeptResponse> deptResponseList;

    private List<SysRoleResponse> roleResponseList;
}
