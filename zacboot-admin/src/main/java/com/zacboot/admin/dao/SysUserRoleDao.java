package com.zacboot.admin.dao;

import cn.hutool.db.Page;
import com.zacboot.admin.beans.entity.SysUserRole;
import com.zacboot.admin.beans.vos.request.UserRoleRequest;

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

    List<SysUserRole> queryUserRoles(String userUuid);
}