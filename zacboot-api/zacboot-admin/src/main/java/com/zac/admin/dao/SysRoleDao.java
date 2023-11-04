package com.zac.admin.dao;

import cn.hutool.db.Page;
import com.zac.base.bizentity.SysRole;
import com.zac.admin.beans.vos.request.RoleRequest;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysRoleDao {
    Integer add(SysRole sysRole);

    Integer del(SysRole sysRole);

    Integer update(SysRole sysRole);

    List<SysRole> queryPage(RoleRequest roleRequest, Page page);

    List<SysRole> queryAll();

    Long queryPageCount(RoleRequest roleRequest);

    SysRole queryByUuid(String roleUuid);
}