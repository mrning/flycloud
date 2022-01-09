package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysRoleDTO;
import com.zac.flycloud.bean.vos.RoleRequestVO;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysRoleDao {
    Integer add(SysRoleDTO sysRoleDTO);

    Integer del(SysRoleDTO sysRoleDTO);

    Integer update(SysRoleDTO sysRoleDTO);

    List<SysRoleDTO> queryPage(RoleRequestVO roleRequestVO, Page page);

    List<SysRoleDTO> queryAll();

    Long queryPageCount(RoleRequestVO roleRequestVO);
}