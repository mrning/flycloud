package com.zacboot.autocode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.zacboot.autocode.base.SysBaseServiceImpl;
import com.zacboot.autocode.dao.SysRolePermissionDao;
import com.zacboot.autocode.dto.SysRolePermission;
import com.zacboot.autocode.mapper.SysRolePermissionMapper;
import com.zacboot.autocode.service.SysRolePermissionService;
import com.zacboot.autocode.tablemodel.SysRolePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
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

    public PageResult<SysRolePermission> queryPage(SysRolePermission sysRolePermission) {
        PageResult<SysRolePermission> pageResult = new PageResult<>();
        pageResult.setDataList(sysRolePermissionDao.queryPage(sysRolePermission,new Page(sysRolePermission.getPageNumber(),sysRolePermission.getPageSize())));
        pageResult.setTotal(sysRolePermissionDao.queryPageCount(sysRolePermission).intValue());
        return pageResult;
    }
}