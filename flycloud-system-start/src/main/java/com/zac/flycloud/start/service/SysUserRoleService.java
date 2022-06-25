package com.zac.flycloud.start.service;

import com.zac.flycloud.start.bean.basebean.PageResult;
import com.zac.flycloud.start.bean.entity.SysUserRole;
import com.zac.flycloud.start.bean.dtos.SysUserRoleDTO;
import com.zac.flycloud.start.bean.vos.request.UserRoleRequest;
import com.zac.flycloud.start.bean.vos.response.SysUserRoleResponse;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserRoleService extends SysBaseService<SysUserRole> {
    Integer add(SysUserRoleDTO sysUserRoleDTO);

    Integer del(SysUserRoleDTO sysUserRoleDTO);

    Integer update(SysUserRoleDTO sysUserRoleDTO);

    PageResult<SysUserRoleResponse> queryPage(UserRoleRequest userRoleRequest);
}