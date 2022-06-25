package com.zac.flycloud.start.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.start.bean.entity.SysRole;
import com.zac.flycloud.start.bean.dtos.SysUserRoleDTO;
import com.zac.flycloud.start.bean.vos.request.UserRoleRequest;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysUserRoleDao {
    Integer add(SysUserRoleDTO sysUserRoleDTO);

    Integer del(SysUserRoleDTO sysUserRoleDTO);

    Integer update(SysUserRoleDTO sysUserRoleDTO);

    List<SysUserRoleDTO> queryPage(UserRoleRequest userRoleRequest, Page page);

    Long queryPageCount(UserRoleRequest userRoleRequest);

    List<SysRole> queryUserRoles(String userUuid);
}