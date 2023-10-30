package com.lqjk.admin.dao.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lqjk.base.bizentity.SysRole;
import com.lqjk.admin.beans.example.SysRoleExample;
import com.lqjk.admin.beans.vos.request.RoleRequest;
import com.lqjk.admin.dao.SysRoleDao;
import com.lqjk.admin.mapper.SysRoleMapper;
import com.lqjk.base.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        sysRole.setDeleted(true);
        sysRole.setUpdateTime(LocalDateTime.now());

        return sysRoleMapper.updateByExampleSelective(sysRole, buildExample(sysRole));
    }

    public Integer update(SysRole sysRole) {
        sysRole.setUpdateTime(LocalDateTime.now());
        sysRole.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        return sysRoleMapper.updateByExampleSelective(sysRole, buildExample(sysRole));
    }

    public List<SysRole> queryPage(RoleRequest roleRequest, Page page) {
        return sysRoleMapper.selectByExampleWithRowbounds(buildExample(SysRole.convertByRequest(roleRequest)), new RowBounds(page.getPageNumber(), page.getPageSize()));
    }

    public Long queryPageCount(RoleRequest roleRequest) {
        return sysRoleMapper.countByExample(buildExample(SysRole.convertByRequest(roleRequest)));
    }

    private SysRoleExample buildExample(SysRole sysRole) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        SysRoleExample.Criteria criteria = sysRoleExample.createCriteria();
        if (StringUtils.isNotBlank(sysRole.getUuid())){
            criteria.andUuidEqualTo(sysRole.getUuid());
        }
        if (StringUtils.isNotBlank(sysRole.getRoleName())){
            criteria.andRoleNameLike("%"+sysRole.getRoleName()+"%");
        }
        if (StringUtils.isNotBlank(sysRole.getRoleCode())){
            criteria.andRoleCodeEqualTo(sysRole.getRoleCode());
        }
        criteria.andDeletedEqualTo(false);
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