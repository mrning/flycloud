package com.zac.flycloud.security.dao.impl;

import com.zac.flycloud.security.bean.SysRole;
import com.zac.flycloud.security.dao.SysUserRoleDao;
import com.zac.flycloud.security.dao.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysUserRoleDaoImpl implements SysUserRoleDao {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> getRolesByUserUuid(String userUuid) {
        return sysUserRoleMapper.getRolesByUserUuid(userUuid);
    }
}
