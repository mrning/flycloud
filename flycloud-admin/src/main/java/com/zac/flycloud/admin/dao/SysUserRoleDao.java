package com.zac.flycloud.admin.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.admin.beans.entity.SysRole;
import com.zac.flycloud.admin.beans.entity.SysUserRole;
import com.zac.flycloud.admin.beans.vos.request.UserRoleRequest;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserRoleDao {
    Integer add(SysUserRole sysUserRole);

    Integer del(SysUserRole sysUserRole);

    Integer update(SysUserRole sysUserRole);

    List<SysUserRole> queryPage(UserRoleRequest userRoleRequest, Page page);

    Long queryPageCount(UserRoleRequest userRoleRequest);

    List<SysRole> queryUserRoles(String userUuid);
}