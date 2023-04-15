package com.zacboot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.zac.system.core.entity.admin.SysRolePermission;
import com.zacboot.admin.dao.SysRolePermissionDao;
import com.zacboot.admin.mapper.SysRolePermissionMapper;
import com.zacboot.admin.service.SysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

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
        sysRolePermission.setUuid(UUID.fastUUID().toString(true));
        sysRolePermission.setCreateTime(LocalDateTime.now());
        sysRolePermission.setDeleted(false);
        return sysRolePermissionDao.add(sysRolePermission);
    }

    public Integer del(SysRolePermission sysRolePermission) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysRolePermission), "不能全部属性为空，会删除全表数据");
        return sysRolePermissionDao.del(sysRolePermission);
    }

    public Integer update(SysRolePermission sysRolePermission) {
        return sysRolePermissionDao.update(sysRolePermission);
    }

    @Override
    public List<SysRolePermission> queryByRoleUuid(String roleUuid) {
        Assert.isTrue(StringUtils.isNotBlank(roleUuid), "角色uuid不能为空");
        return sysRolePermissionDao.queryByRoleUuid(roleUuid);
    }

    @Override
    public Integer delByRoleUuid(String roleUuid) {
        Assert.isTrue(StringUtils.isNotBlank(roleUuid), "角色uuid不能为空");
        return sysRolePermissionDao.delByRoleUuid(roleUuid);
    }
}