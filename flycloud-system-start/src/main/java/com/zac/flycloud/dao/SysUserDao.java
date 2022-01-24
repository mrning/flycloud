package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysUser;
import com.zac.flycloud.bean.vos.UserRequestVO;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
public interface SysUserDao{
    Integer add(SysUser sysUser);

    Integer del(SysUser sysUser);

    Integer update(SysUser sysUser);

    List<SysUser> queryPage(UserRequestVO userRequestVO, Page page);

    Long queryPageCount(UserRequestVO userRequestVO);

    SysUser getUserByName(String username);
}