package com.zacboot.admin.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.zacboot.admin.beans.entity.SysRole;
import com.zacboot.admin.beans.entity.SysRolePermission;
import com.zacboot.admin.beans.vos.request.RoleAddRequest;
import com.zacboot.admin.beans.vos.request.RoleRequest;
import com.zacboot.admin.dao.SysRoleDao;
import com.zacboot.admin.dao.SysUserRoleDao;
import com.zacboot.admin.mapper.SysRoleMapper;
import com.zacboot.admin.service.SysRolePermissionService;
import com.zacboot.admin.service.SysRoleService;
import com.zacboot.common.base.basebeans.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    public Integer add(RoleAddRequest roleAddRequest) {
        SysRole sysRole = SysRole.convertByAddRequest(roleAddRequest);
        sysRole.setUuid(UUID.fastUUID().toString(true));

        if (!roleAddRequest.getPermissions().isEmpty()) {
            for (String permissionUuid : roleAddRequest.getPermissions()) {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleUuid(sysRole.getUuid());
                sysRolePermission.setPermissionUuid(permissionUuid);
                sysRolePermissionService.add(sysRolePermission);
            }
        }

        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setDeleted(false);
        return sysRoleDao.add(sysRole);
    }

    public Integer del(SysRole sysRole) {
        Assert.isTrue(StringUtils.isNotBlank(sysRole.getUuid()), "删除失败，参数异常");

        if (!CollectionUtils.isEmpty(sysRolePermissionService.queryByRoleUuid(sysRole.getUuid()))) {
            sysRolePermissionService.delByRoleUuid(sysRole.getUuid());
        }
        sysRole.setDeleted(true);
        return sysRoleDao.update(sysRole);
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
        return sysUserRoleDao.queryUserRoles(userUuid).stream()
                .map(sysUserRole -> sysRoleDao.queryByUuid(sysUserRole.getRoleUuid()))
                .collect(Collectors.toList());
    }
}