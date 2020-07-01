package com.zac.flycloud.dao;

import com.zac.flycloud.tablemodel.FcUser;

public interface UserDao {

    FcUser findByUserName(String username);

}
