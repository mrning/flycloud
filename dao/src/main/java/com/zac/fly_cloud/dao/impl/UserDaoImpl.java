package com.zac.fly_cloud.dao.impl;

import com.zac.fly_cloud.dao.UserDao;
import com.zac.fly_cloud.mapper.UserMapper;
import com.zac.fly_cloud.tablemodel.FcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public FcUser findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
