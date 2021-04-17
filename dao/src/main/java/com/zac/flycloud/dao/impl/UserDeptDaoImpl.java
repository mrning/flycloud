package com.zac.flycloud.dao.impl;

import com.zac.flycloud.dao.UserDeptDao;
import com.zac.flycloud.tablemodel.SysDept;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDeptDaoImpl implements UserDeptDao {

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    @Override
    public List<SysDept> getDeptsByUserUuid(String userUuid) {
        return sysUserDeptMapper.getDeptsByUserUuid(userUuid);
    }
}
