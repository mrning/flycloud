package com.zac.admin.dao;

import cn.hutool.db.Page;
import com.zac.base.bizentity.SysPermission;
import com.zac.admin.beans.vos.request.PermissionRequest;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2022年12月11日星期日
 * @author zac
 */
public interface SysPermissionDao {
    Integer add(SysPermission sysPermission);

    Integer del(SysPermission sysPermission);

    Integer update(SysPermission sysPermission);

    List<SysPermission> queryPage(PermissionRequest permissionRequest, Page page);

    Long queryPageCount(PermissionRequest permissionRequest);

    Integer getMaxSortNo(String parentUuid);

    SysPermission getByUuid(String uuid);
}