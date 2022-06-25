package com.zac.flycloud.start.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.start.bean.entity.SysUser;
import com.zac.flycloud.start.bean.vos.request.UserRequest;

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

    List<SysUser> queryPage(UserRequest userRequest, Page page);

    Long queryPageCount(UserRequest userRequest);

    SysUser getUserByName(String username);

    SysUser queryByUuid(String uuid);
}