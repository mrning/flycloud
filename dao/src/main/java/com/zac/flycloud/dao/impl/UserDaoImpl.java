package com.zac.flycloud.dao.impl;

import com.zac.flycloud.dao.UserDao;
import com.zac.flycloud.mapper.UserMapper;
import com.zac.flycloud.entity.tablemodel.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUser getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}
