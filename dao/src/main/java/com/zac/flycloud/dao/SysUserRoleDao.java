package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.dto.SysUserRoleDTO;
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

    List<SysUserRoleDTO> queryPage(SysUserRoleDTO sysUserRoleDTO, Page page);

    Long queryPageCount(SysUserRoleDTO sysUserRoleDTO);
}