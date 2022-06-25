package com.zac.flycloud.start.dao;


import com.zac.flycloud.start.bean.entity.SysRole;

import java.util.List;

public interface UserRoleDao {

    List<SysRole> getRolesByUserUuid(String userUuid);
}
