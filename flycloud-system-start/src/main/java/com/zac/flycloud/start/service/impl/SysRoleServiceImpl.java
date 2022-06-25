package com.zac.flycloud.start.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.start.bean.basebean.PageResult;
import com.zac.flycloud.start.bean.entity.SysRole;
import com.zac.flycloud.start.bean.vos.request.RoleRequest;
import com.zac.flycloud.start.dao.SysRoleDao;
import com.zac.flycloud.start.dao.SysUserRoleDao;
import com.zac.flycloud.start.dao.mapper.SysRoleMapper;
import com.zac.flycloud.start.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends SysBaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    public Integer add(SysRole sysRole) {
        return sysRoleDao.add(sysRole);
    }

    public Integer del(SysRole sysRole) {
        Assert.isTrue(BeanUtil.isEmpty(sysRole),"不能全部属性为空，会删除全表数据");
        return sysRoleDao.del(sysRole);
    }

    public Integer update(SysRole sysRole) {
        return sysRoleDao.update(sysRole);
    }

    public PageResult<SysRole> queryPage(RoleRequest roleRequest) {
        PageResult<SysRole> pageResult = new PageResult<>();
        pageResult.setDataList(sysRoleDao.queryPage(roleRequest,new Page(roleRequest.getPageNumber(), roleRequest.getPageSize())));
        pageResult.setTotal(sysRoleDao.queryPageCount(roleRequest).intValue());
        return pageResult;
    }

    @Override
    public List<SysRole> queryAll() {
        return sysRoleDao.queryAll();
    }

    @Override
    public List<SysRole> queryUserRoles(String userUuid) {
        return sysUserRoleDao.queryUserRoles(userUuid);
    }
}