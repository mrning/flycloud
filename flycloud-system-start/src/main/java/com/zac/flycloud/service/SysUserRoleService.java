package com.zac.flycloud.service;

import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.dto.SysUserRole;
import com.zac.flycloud.bean.tablemodel.SysUserRoleDTO;
import com.zac.flycloud.bean.vos.request.UserRoleRequest;
import com.zac.flycloud.bean.vos.response.SysUserRoleResponse;

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