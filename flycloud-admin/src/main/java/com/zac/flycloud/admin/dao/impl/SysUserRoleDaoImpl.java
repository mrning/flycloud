package com.zac.flycloud.admin.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.admin.beans.entity.SysRole;
import com.zac.flycloud.admin.beans.entity.SysUserRole;
import com.zac.flycloud.admin.beans.entity.example.SysUserRoleExample;
import com.zac.flycloud.admin.beans.vos.request.UserRoleRequest;
import com.zac.flycloud.admin.dao.SysUserRoleDao;
import com.zac.flycloud.admin.dao.mapper.SysUserRoleMapper;
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
public class SysUserRoleDaoImpl implements SysUserRoleDao {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public Integer add(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insertSelective(sysUserRole);
    }

    public Integer del(SysUserRole sysUserRole) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.deleteByExample(sysUserRoleExample);
    }

    public Integer update(SysUserRole sysUserRole) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.updateByExampleSelective(sysUserRole, sysUserRoleExample);
    }

    public List<SysUserRole> queryPage(UserRoleRequest userRoleRequest, Page page) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.selectByExampleWithRowbounds(sysUserRoleExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserRoleRequest userRoleRequest) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        return sysUserRoleMapper.countByExample(sysUserRoleExample);
    }

    @Override
    public List<SysRole> queryUserRoles(String userUuid) {
        return sysUserRoleMapper.getRolesByUserUuid(userUuid);
    }
}