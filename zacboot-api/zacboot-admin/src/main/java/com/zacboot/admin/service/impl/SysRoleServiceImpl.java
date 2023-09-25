package com.zacboot.admin.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.zacboot.core.entity.admin.SysRole;
import com.zacboot.core.entity.admin.SysRolePermission;
import com.zacboot.admin.beans.vos.request.RoleAddRequest;
import com.zacboot.admin.beans.vos.request.RoleRequest;
import com.zacboot.admin.beans.vos.request.RoleUpdateRequest;
import com.zacboot.admin.beans.vos.response.RolePageResponse;
import com.zacboot.admin.dao.SysRoleDao;
import com.zacboot.admin.dao.SysUserRoleDao;
import com.zacboot.admin.mapper.SysRoleMapper;
import com.zacboot.admin.service.SysRolePermissionService;
import com.zacboot.admin.service.SysRoleService;
import com.zacboot.core.util.SysUtil;
import com.zacboot.common.base.basebeans.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2021年4月30日星期五
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
        SysRole sysRole = SysRole.convertByRequest(roleAddRequest);
        sysRole.setUuid(UUID.fastUUID().toString(true));

        if (!roleAddRequest.getPermissions().isEmpty()) {
            for (String permissionUuid : roleAddRequest.getPermissions()) {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleUuid(sysRole.getUuid());
                sysRolePermission.setPermissionUuid(permissionUuid);
                sysRolePermissionService.add(sysRolePermission);
            }
        }

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

    public Integer update(RoleUpdateRequest request) {
        if (!CollectionUtils.isEmpty(request.getPermissions())) {
            sysRolePermissionService.delByRoleUuid(request.getUuid());
            request.getPermissions().forEach(s -> {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleUuid(request.getUuid());
                sysRolePermission.setPermissionUuid(s);
                sysRolePermission.setCreateUser(SysUtil.getCurrentUser().getNickname());
                sysRolePermissionService.add(sysRolePermission);
            });
        }
        return sysRoleDao.update(SysRole.convertByRequest(request));
    }

    public PageResult<RolePageResponse> queryPage(RoleRequest roleRequest) {
        PageResult<RolePageResponse> pageResult = new PageResult<>();
        List<RolePageResponse> sysRoles = sysRoleDao.queryPage(roleRequest, new Page(roleRequest.getPageNumber(), roleRequest.getPageSize()))
                .stream().map(sysRole -> {
                    RolePageResponse rolePageResponse = RolePageResponse.convertByEntity(sysRole);
                    List<String> permissionUuids = sysRolePermissionService.queryByRoleUuid(sysRole.getUuid()).stream().map(SysRolePermission::getPermissionUuid).collect(Collectors.toList());
                    rolePageResponse.setPermissions(permissionUuids);
                    return rolePageResponse;
                }).collect(Collectors.toList());

        pageResult.setDataList(sysRoles);
        pageResult.setTotal(sysRoleDao.queryPageCount(roleRequest));
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