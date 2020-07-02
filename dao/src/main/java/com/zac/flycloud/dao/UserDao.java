package com.zac.flycloud.dao;

import com.zac.flycloud.tablemodel.SysUser;

public interface UserDao {

    SysUser findByUserName(String username);

}
