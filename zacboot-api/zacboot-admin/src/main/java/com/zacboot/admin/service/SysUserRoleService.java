package com.zacboot.admin.service;

import com.zacboot.core.entity.admin.SysUserRole;
import com.zacboot.admin.beans.vos.request.UserRoleRequest;
import com.zacboot.admin.beans.vos.response.SysUserRoleResponse;
import com.zacboot.common.base.basebeans.PageResult;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserRoleService extends SysBaseService<SysUserRole> {
    Integer add(SysUserRole sysUserRole);

    Integer del(SysUserRole sysUserRole);

    Integer update(SysUserRole sysUserRole);

    PageResult<SysUserRoleResponse> queryPage(UserRoleRequest userRoleRequest);

    List<SysUserRole> queryRolesByUserUuid(String userUuid);

    Integer updateByUserUuid(String userUuid, List<String> roleUuids);

    Integer delByUserUuid(String userUuid);
}