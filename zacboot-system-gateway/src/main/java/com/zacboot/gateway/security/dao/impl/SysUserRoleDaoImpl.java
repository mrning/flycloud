package com.zacboot.gateway.security.dao.impl;

import com.zacboot.gateway.security.bean.SysRole;
import com.zacboot.gateway.security.dao.SysUserRoleDao;
import com.zacboot.gateway.security.dao.mapper.SysUserRoleMapper;
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
