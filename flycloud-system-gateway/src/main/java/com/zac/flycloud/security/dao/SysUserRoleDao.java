package com.zac.flycloud.security.dao;


import com.zac.flycloud.security.bean.SysRole;

import java.util.List;

public interface SysUserRoleDao {

    List<SysRole> getRolesByUserUuid(String userUuid);
}
