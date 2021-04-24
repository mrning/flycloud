package com.zac.flycloud.service;

import cn.hutool.db.PageResult;
import com.zac.flycloud.dto.SysUserDTO;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
public interface SysUserService {
    Integer add(SysUserDTO sysUserDTO);

    Integer del(SysUserDTO sysUserDTO);

    Integer update(SysUserDTO sysUserDTO);

    PageResult<SysUserDTO> queryPage(SysUserDTO sysUserDTO);
}