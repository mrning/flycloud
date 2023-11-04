package com.zac.admin.service;

import com.zac.base.bizentity.SysRole;
import com.zac.admin.beans.vos.request.RoleAddRequest;
import com.zac.admin.beans.vos.request.RoleRequest;
import com.zac.admin.beans.vos.request.RoleUpdateRequest;
import com.zac.admin.beans.vos.response.RolePageResponse;
import com.zac.base.basebeans.PageResult;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysRoleService extends SysBaseService<SysRole> {
    Integer add(RoleAddRequest roleAddRequest);

    Integer del(SysRole sysRole);

    Integer update(RoleUpdateRequest request);

    PageResult<RolePageResponse> queryPage(RoleRequest roleRequest);

    List<SysRole> queryAll();

    List<SysRole> queryUserRoles(String userUuid);

}