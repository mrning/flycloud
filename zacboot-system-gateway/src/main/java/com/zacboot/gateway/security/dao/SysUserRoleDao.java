package com.zacboot.gateway.security.dao;


import com.zacboot.gateway.security.bean.SysRole;

import java.util.List;

public interface SysUserRoleDao {

    List<SysRole> getRolesByUserUuid(String userUuid);
}
