package com.zac.flycloud.security.dao;

import com.zac.flycloud.security.bean.SysUser;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
public interface SysUserDao {

    SysUser getUserByName(String username);
}