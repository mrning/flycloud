package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.vos.UserRequestVO;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
public interface SysUserDao{
    Integer add(SysUserDTO sysUserDTO);

    Integer del(SysUserDTO sysUserDTO);

    Integer update(SysUserDTO sysUserDTO);

    List<SysUserDTO> queryPage(UserRequestVO userRequestVO, Page page);

    Long queryPageCount(UserRequestVO userRequestVO);
}