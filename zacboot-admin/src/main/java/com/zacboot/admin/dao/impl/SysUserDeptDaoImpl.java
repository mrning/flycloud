package com.zacboot.admin.dao.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.system.core.entity.admin.SysDept;
import com.zac.system.core.entity.admin.SysUser;
import com.zac.system.core.entity.admin.SysUserDept;
import com.zac.system.core.entity.admin.SysUserRole;
import com.zacboot.admin.beans.example.SysUserDeptExample;
import com.zacboot.admin.beans.vos.request.UserDeptRequest;
import com.zacboot.admin.dao.SysUserDeptDao;
import com.zacboot.admin.mapper.SysUserDeptMapper;
import com.zacboot.admin.utils.SysUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SysUserDeptDaoImpl implements SysUserDeptDao {

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    public Integer add(SysUserDept sysUserDept) {
        sysUserDept.setUuid(UUID.randomUUID().toString(true));
        sysUserDept.setCreateTime(LocalDateTime.now());
        sysUserDept.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysUserDept.setDeleted(false);
        return sysUserDeptMapper.insertSelective(sysUserDept);
    }

    public Integer del(SysUserDept sysUserDept) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.deleteByExample(sysUserDeptExample);
    }

    public Integer update(SysUserDept sysUserDept) {
        sysUserDept.setUpdateTime(LocalDateTime.now());
        sysUserDept.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.updateByExampleSelective(sysUserDept, sysUserDeptExample);
    }

    public List<SysUserDept> queryPage(UserDeptRequest userDeptRequest, Page page) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        sysUserDeptExample.createCriteria().andDeletedEqualTo(false);
        return sysUserDeptMapper.selectByExampleWithRowbounds(sysUserDeptExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserDeptRequest userDeptRequest) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        return sysUserDeptMapper.countByExample(sysUserDeptExample);
    }

    @Override
    public List<SysDept> getDeptsByUserUuid(String userUuid) {
        return sysUserDeptMapper.getDeptsByUserUuid(userUuid);
    }

    @Override
    public List<SysUser> getUsersByDeptUuid(String deptUuid) {
        return sysUserDeptMapper.getUsersByDeptUuid(deptUuid);
    }

    @Override
    public Integer delByUserUuid(String userUuid) {
        SysUserDeptExample sysUserDeptExample = new SysUserDeptExample();
        sysUserDeptExample.createCriteria().andUserUuidEqualTo(userUuid);
        return sysUserDeptMapper.deleteByExample(sysUserDeptExample);
    }

    @Override
    public List<SysUserDept> queryUserDepts(String userUuid) {
        return sysUserDeptMapper.selectList(new LambdaQueryWrapper<SysUserDept>().eq(SysUserDept::getUserUuid,userUuid));
    }
}
