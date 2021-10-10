package com.zac.flycloud.service;

import com.zac.flycloud.basebean.PageResult;
import com.zac.flycloud.base.SysBaseService;
import com.zac.flycloud.dto.SysRoleDTO;
import com.zac.flycloud.tablemodel.SysRole;
import com.zac.flycloud.vos.RoleRequestVO;

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
}