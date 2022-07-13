package com.zacboot.admin.service;

import com.zacboot.admin.beans.entity.SysRole;
import com.zacboot.admin.beans.vos.request.RoleRequest;
import com.zacboot.common.base.basebeans.PageResult;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysRoleService extends SysBaseService<SysRole> {
    Integer add(SysRole sysRole);

    Integer del(SysRole sysRole);

    Integer update(SysRole sysRole);

    PageResult<SysRole> queryPage(RoleRequest roleRequest);

    List<SysRole> queryAll();

    List<SysRole> queryUserRoles(String userUuid);

}