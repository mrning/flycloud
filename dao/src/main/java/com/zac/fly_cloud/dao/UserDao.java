package com.zac.fly_cloud.dao;

import com.zac.fly_cloud.tablemodel.FcUser;

public interface UserDao {

    FcUser findByUserName(String username);

}
