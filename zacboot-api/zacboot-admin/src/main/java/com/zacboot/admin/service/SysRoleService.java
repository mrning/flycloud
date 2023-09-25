package com.zacboot.admin.service;

import com.zacboot.core.entity.admin.SysRole;
import com.zacboot.admin.beans.vos.request.RoleAddRequest;
import com.zacboot.admin.beans.vos.request.RoleRequest;
import com.zacboot.admin.beans.vos.request.RoleUpdateRequest;
import com.zacboot.admin.beans.vos.response.RolePageResponse;
import com.zacboot.common.base.basebeans.PageResult;

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