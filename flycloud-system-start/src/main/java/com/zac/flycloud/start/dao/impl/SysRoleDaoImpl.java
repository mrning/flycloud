package com.zac.flycloud.start.dao.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zac.flycloud.start.bean.entity.SysRole;
import com.zac.flycloud.start.bean.entity.example.SysRoleExample;
import com.zac.flycloud.start.bean.vos.request.RoleRequest;
import com.zac.flycloud.start.dao.SysRoleDao;
import com.zac.flycloud.start.dao.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return sysRoleMapper.insertSelective(sysRole);
    }

    public Integer del(SysRole sysRole) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        return sysRoleMapper.deleteByExample(sysRoleExample);
    }

    public Integer update(SysRole sysRole) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        return sysRoleMapper.updateByExampleSelective(sysRole, sysRoleExample);
    }

    public List<SysRole> queryPage(RoleRequest roleRequest, Page page) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        return sysRoleMapper.selectByExampleWithRowbounds(sysRoleExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(RoleRequest roleRequest) {
        SysRoleExample sysRoleExample = new SysRoleExample();
        return sysRoleMapper.countByExample(sysRoleExample);
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