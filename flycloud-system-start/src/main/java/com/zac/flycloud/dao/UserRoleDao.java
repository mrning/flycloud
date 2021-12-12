package com.zac.flycloud.dao;


import com.zac.flycloud.bean.tablemodel.SysRole;

import java.util.List;

public interface UserRoleDao {

    List<SysRole> getRolesByUserUuid(String userUuid);
}
