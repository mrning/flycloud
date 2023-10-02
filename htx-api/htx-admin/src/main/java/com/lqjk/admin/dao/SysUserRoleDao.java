package com.lqjk.admin.dao;

import cn.hutool.db.Page;
import com.lqjk.admin.beans.vos.request.UserRoleRequest;
import com.lqjk.admin.entity.SysUserRole;

import java.util.List;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2021年4月30日星期五
 */
public interface SysUserRoleDao {
    Integer add(SysUserRole sysUserRole);

    Integer del(SysUserRole sysUserRole);

    Integer delByUserUuid(String userUuid);

    Integer update(SysUserRole sysUserRole);

    List<SysUserRole> queryPage(UserRoleRequest userRoleRequest, Page page);

    Long queryPageCount(UserRoleRequest userRoleRequest);

    List<SysUserRole> queryUserRoles(String userUuid);

}