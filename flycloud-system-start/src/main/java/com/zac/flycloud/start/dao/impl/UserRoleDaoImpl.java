package com.zac.flycloud.start.dao.impl;

import com.zac.flycloud.start.bean.entity.SysRole;
import com.zac.flycloud.start.dao.UserRoleDao;
import com.zac.flycloud.start.dao.mapper.SysUserRoleMapper;
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
