package com.zac.flycloud.service;

import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.dto.SysRoleDTO;
import com.zac.flycloud.bean.tablemodel.SysRole;
import com.zac.flycloud.bean.vos.RoleRequestVO;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysRoleService extends SysBaseService<SysRole> {
    Integer add(SysRoleDTO sysRoleDTO);

    Integer del(SysRoleDTO sysRoleDTO);

    Integer update(SysRoleDTO sysRoleDTO);

    PageResult<SysRoleDTO> queryPage(RoleRequestVO roleRequestVO);

    List<SysRoleDTO> queryAll();
}