package com.zac.flycloud.sys;

import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.tablemodel.SysUser;

public interface SysUserService<T> extends SysBaseAPI<SysUser> {

    /**
     * 根据用户账号查询用户信息
     *
     * @param username
     * @return
     */
    SysUser getUserByName(String username);

    /**
     * 重置密码
     *
     * @param username
     * @param oldpassword
     * @param newpassword
     * @param confirmpassword
     * @return
     */
    DataResponseResult<?> resetPassword(String username, String oldpassword, String newpassword, String confirmpassword);

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    DataResponseResult<?> changePassword(SysUser sysUser);

    /**
     * 根据手机号获取用户名和密码
     */
    SysUser getUserByPhone(String phone);


    /**
     * 根据邮箱获取用户
     */
    SysUser getUserByEmail(String email);

    /**
     * 校验用户是否有效
     *
     * @param sysUser
     * @return
     */
    DataResponseResult checkUserIsEffective(SysUser sysUser);

    /**
     * 查詢用戶信息
     * @param sysUser
     * @return
     * @throws Exception
     */
    DataResponseResult userInfo(SysUser sysUser) throws Exception;

}
