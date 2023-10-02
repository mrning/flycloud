package com.lqjk.admin.dao.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lqjk.admin.beans.example.SysUserRoleExample;
import com.lqjk.admin.beans.vos.request.UserRoleRequest;
import com.lqjk.admin.dao.SysUserRoleDao;
import com.lqjk.admin.entity.SysUserRole;
import com.lqjk.admin.mapper.SysUserRoleMapper;
import com.lqjk.base.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Repository
@Slf4j
public class SysUserRoleDaoImpl implements SysUserRoleDao {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public Integer add(SysUserRole sysUserRole) {
        sysUserRole.setUuid(UUID.randomUUID().toString(true));
        sysUserRole.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysUserRole.setCreateTime(LocalDateTime.now());
        sysUserRole.setDeleted(false);
        return sysUserRoleMapper.insertSelective(sysUserRole);
    }

    public Integer del(SysUserRole sysUserRole) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.deleteByExample(sysUserRoleExample);
    }

    @Override
    public Integer delByUserUuid(String userUuid) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserUuidEqualTo(userUuid);
        return sysUserRoleMapper.deleteByExample(sysUserRoleExample);
    }

    public Integer update(SysUserRole sysUserRole) {
        sysUserRole.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        sysUserRole.setUpdateTime(LocalDateTime.now());
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.updateByExampleSelective(sysUserRole, sysUserRoleExample);
    }

    public List<SysUserRole> queryPage(UserRoleRequest userRoleRequest, Page page) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.selectByExampleWithRowbounds(sysUserRoleExample, new RowBounds(page.getPageNumber(), page.getPageSize()));
    }

    public Long queryPageCount(UserRoleRequest userRoleRequest) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.countByExample(sysUserRoleExample);
    }

    @Override
    public List<SysUserRole> queryUserRoles(String userUuid) {
        return sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserUuid,userUuid));
    }
}