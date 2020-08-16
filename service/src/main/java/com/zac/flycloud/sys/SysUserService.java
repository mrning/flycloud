package com.zac.flycloud.sys;

import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.entity.tablemodel.SysUser;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysUserService extends IService<SysUser>{

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

    SysUser getUserByName(String username);


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

}
