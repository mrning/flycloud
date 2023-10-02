package com.lqjk.admin.dao.impl;

import cn.hutool.db.Page;
import com.lqjk.admin.entity.SysRolePermission;
import com.lqjk.admin.beans.example.SysRolePermissionExample;
import com.lqjk.admin.dao.SysRolePermissionDao;
import com.lqjk.admin.mapper.SysRolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2022年11月3日星期四
 * @author zac
 */
@Repository
@Slf4j
public class SysRolePermissionDaoImpl implements SysRolePermissionDao {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    public Integer add(SysRolePermission sysRolePermission) {
        return sysRolePermissionMapper.insertSelective(sysRolePermission);
    }

    public Integer del(SysRolePermission sysRolePermission) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.deleteByExample(sysRolePermissionExample);
    }

    public Integer update(SysRolePermission sysRolePermission) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.updateByExampleSelective(sysRolePermission,sysRolePermissionExample);
    }

    public List<SysRolePermission> queryPage(SysRolePermission sysRolePermission, Page page) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.selectByExampleWithRowbounds(sysRolePermissionExample, new RowBounds(page.getPageNumber(), page.getPageSize()));
    }

    public Long queryPageCount(SysRolePermission sysRolePermission) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        return sysRolePermissionMapper.countByExample(sysRolePermissionExample);
    }

    @Override
    public List<SysRolePermission> queryByRoleUuid(String roleUuid) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        sysRolePermissionExample.createCriteria().andRoleUuidEqualTo(roleUuid);
        return sysRolePermissionMapper.selectByExample(sysRolePermissionExample);
    }

    @Override
    public Integer delByRoleUuid(String roleUuid) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        sysRolePermissionExample.createCriteria().andRoleUuidEqualTo(roleUuid);
        return sysRolePermissionMapper.deleteByExample(sysRolePermissionExample);
    }
}