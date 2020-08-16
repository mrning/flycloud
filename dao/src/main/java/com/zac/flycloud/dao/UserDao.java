package com.zac.flycloud.dao;

import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysRole;
import com.zac.flycloud.entity.tablemodel.SysUser;

import java.util.List;

public interface UserDao {

    SysUser getUserByName(String username);

    List<SysDept> getDeptsByUserUuid(String userUuid);

    List<SysRole> getRolesByUserUuid(String userUuid);
}
