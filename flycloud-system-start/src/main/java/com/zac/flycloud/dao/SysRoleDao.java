package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysRole;
import com.zac.flycloud.bean.vos.request.RoleRequest;

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
}