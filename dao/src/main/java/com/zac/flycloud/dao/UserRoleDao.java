package com.zac.flycloud.dao;

import com.zac.flycloud.entity.tablemodel.SysRole;

import java.util.List;

public interface UserRoleDao {

    List<SysRole> getRolesByUserUuid(String userUuid);
}
