package com.zac.flycloud.admin.service;

import com.zac.flycloud.admin.beans.entity.SysRole;
import com.zac.flycloud.admin.beans.vos.request.RoleRequest;
import com.zac.flycloud.common.basebeans.PageResult;

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