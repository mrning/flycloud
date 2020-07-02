package com.zac.flycloud.dao.impl;

import com.zac.flycloud.dao.UserDao;
import com.zac.flycloud.mapper.UserMapper;
import com.zac.flycloud.tablemodel.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUser findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
