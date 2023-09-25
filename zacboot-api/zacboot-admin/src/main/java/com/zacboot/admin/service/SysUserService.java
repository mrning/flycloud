package com.zacboot.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.core.response.admin.SysUserDeptAndRoleInfo;
import com.zacboot.core.entity.admin.SysUser;
import com.zacboot.admin.beans.vos.request.RegisRequest;
import com.zacboot.admin.beans.vos.request.UserAddRequest;
import com.zacboot.core.request.admin.UserRequest;
import com.zacboot.admin.beans.vos.request.UserUpdateRequest;
import com.zacboot.admin.beans.vos.response.SysUserResponse;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
public interface SysUserService extends SysBaseService<SysUser> {
    Integer add(UserAddRequest userAddRequest);

    Integer del(SysUser sysUser);

    Integer update(UserUpdateRequest userUpdateRequest, String token);

    PageResult<SysUserResponse> queryPage(UserRequest userRequest);

    /**
     * 根据用户账号查询用户信息
     *
     * @param username
     * @return
     */
    SysUser getUserByName(String username);

    /**
     * 根据手机号获取用户名和密码
     */
    SysUser getUserByPhone(String phone);

    /**
     * 根据邮箱获取用户
     */
    SysUser getUserByEmail(String mail);

    /**
     * 重置密码
     *
     * @param username
     * @param oldpassword
     * @param newpassword
     * @param confirmpassword
     * @return
     */
    Result<?> resetPassword(String username, String oldpassword, String newpassword, String confirmpassword);

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    Result<?> changePassword(SysUser sysUser);

    /**
     * 校验用户是否有效
     *
     * @param sysUser
     * @return
     */
    Result checkUserIsEffective(SysUser sysUser);

    /**
     * 查詢用戶信息
     * @param sysUser
     * @return
     * @throws Exception
     */
    JSONObject userInfo(SysUser sysUser);

    /**
     * regis
     */
    boolean register(RegisRequest regisRequest);

    /**
     * 登出
     */
    boolean logout(String token);

    SysUserDeptAndRoleInfo deptAndRoleInfo(UserRequest userRequest);
}