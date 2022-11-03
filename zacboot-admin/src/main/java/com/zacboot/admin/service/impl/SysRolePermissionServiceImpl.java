package com.zacboot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zacboot.admin.beans.entity.SysRolePermission;
import com.zacboot.admin.dao.SysRolePermissionDao;
import com.zacboot.admin.mapper.SysRolePermissionMapper;
import com.zacboot.admin.service.SysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2022年11月3日星期四
 * @author zac
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl extends SysBaseServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {
    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

    public Integer add(SysRolePermission sysRolePermission) {
        return sysRolePermissionDao.add(sysRolePermission);
    }

    public Integer del(SysRolePermission sysRolePermission) {
        Assert.isTrue(BeanUtil.isEmpty(sysRolePermission),"不能全部属性为空，会删除全表数据");
        return sysRolePermissionDao.del(sysRolePermission);
    }

    public Integer update(SysRolePermission sysRolePermission) {
        return sysRolePermissionDao.update(sysRolePermission);
    }
}