package com.zac.flycloud.dao;

import com.zac.flycloud.entity.tablemodel.SysUser;

public interface UserDao {

    SysUser getUserByName(String username);

}
