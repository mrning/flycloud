package com.zac.flycloud.dao;


import com.zac.flycloud.bean.dto.SysRole;

import java.util.List;

public interface UserRoleDao {

    List<SysRole> getRolesByUserUuid(String userUuid);
}
