package com.zac.flycloud.dao.impl;

import com.zac.flycloud.dao.UserDao;
import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysRole;
import com.zac.flycloud.mapper.SysUserMapper;
import com.zac.flycloud.entity.tablemodel.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }

    @Override
    public List<SysDept> getDeptsByUserUuid(String userUuid) {
        return sysUserMapper.getDeptsByUserUuid(userUuid);
    }

    @Override
    public List<SysRole> getRolesByUserUuid(String userUuid) {
        return sysUserMapper.getRolesByUserUuid(userUuid);
    }
}
