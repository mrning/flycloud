package com.zac.flycloud.dao.impl;

import com.zac.flycloud.dao.UserRoleDao;
import com.zac.flycloud.entity.tablemodel.SysRole;
import com.zac.flycloud.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> getRolesByUserUuid(String userUuid) {
        return sysUserRoleMapper.getRolesByUserUuid(userUuid);
    }
}
