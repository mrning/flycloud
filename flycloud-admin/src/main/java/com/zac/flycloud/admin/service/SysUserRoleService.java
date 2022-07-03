package com.zac.flycloud.admin.service;

import com.zac.flycloud.admin.beans.entity.SysUserRole;
import com.zac.flycloud.admin.beans.vos.request.UserRoleRequest;
import com.zac.flycloud.admin.beans.vos.response.SysUserRoleResponse;
import com.zac.flycloud.common.basebeans.PageResult;

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
}