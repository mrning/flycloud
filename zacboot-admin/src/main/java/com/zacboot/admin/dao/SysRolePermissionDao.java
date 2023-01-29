package com.zacboot.admin.dao;

import cn.hutool.db.Page;
import com.zacboot.admin.beans.entity.SysRolePermission;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2022年11月3日星期四
 * @author zac
 */
public interface SysRolePermissionDao {
    Integer add(SysRolePermission sysRolePermission);

    Integer del(SysRolePermission sysRolePermission);

    Integer update(SysRolePermission sysRolePermission);

    List<SysRolePermission> queryPage(SysRolePermission sysRolePermission, Page page);

    Long queryPageCount(SysRolePermission sysRolePermission);

    List<SysRolePermission> queryByRoleUuid(String roleUuid);

    Integer delByRoleUuid(String roleUuid);
}