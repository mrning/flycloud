package com.zacboot.admin.dao.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zacboot.admin.beans.entity.SysRole;
import com.zacboot.admin.beans.example.SysRoleExample;
import com.zacboot.admin.beans.vos.request.RoleRequest;
import com.zacboot.admin.dao.SysRoleDao;
import com.zacboot.admin.mapper.SysRoleMapper;
import com.zacboot.admin.utils.SysUtil;
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
public class SysRoleDaoImpl implements SysRoleDao {
    
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public Integer add(SysRole sysRole) {
        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysRole.setDeleted(false);
        return sysRoleMapper.insertSelective(sysRole);
    }

    public Integer del(SysRole sysRole) {
        SysRoleExample s = new SysRoleExample();
        s.createCriteria().andUuidEqualTo(sysRole.getUuid());
        sysRole.setDeleted(true);
        sysRole.setUpdateTime(LocalDateTime.now());
        return sysRoleMapper.updateByExampleSelective(sysRole, s);
    }

    public Integer update(SysRole sysRole) {
        sysRole.setUpdateTime(LocalDateTime.now());
        sysRole.setUpdateUser(SysUtil.getCurrentUser().getNickname());

        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.createCriteria().andUuidEqualTo(sysRole.getUuid());
        return sysRoleMapper.updateByExampleSelective(sysRole, sysRoleExample);
    }

    public List<SysRole> queryPage(RoleRequest roleRequest, Page page) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        return sysRoleMapper.selectByExampleWithRowbounds(buildExample(sysRoleExample), new RowBounds(page.getPageNumber(), page.getPageSize()));
    }

    public Long queryPageCount(RoleRequest roleRequest) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        return sysRoleMapper.countByExample(buildExample(sysRoleExample));
    }

    private SysRoleExample buildExample(SysRoleExample sysRoleExample) {
        sysRoleExample.createCriteria().andDeletedEqualTo(false);
        return sysRoleExample;
    }

    @Override
    public List<SysRole> queryAll() {
        return sysRoleMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public SysRole queryByUuid(String roleUuid) {
        return sysRoleMapper.queryByUuid(roleUuid);
    }
}