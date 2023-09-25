package com.zacboot.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zacboot.auth.beans.domain.SysUser;
import com.zacboot.core.request.auth.AuthLoginRequest;
import com.zacboot.core.request.auth.AuthLogoutRequest;

import java.util.Optional;

/**
 * Created by macro on 2019/10/18.
 */
public interface AdminService extends IService<SysUser> {

    /**
     * 根据用户名获取后台管理员
     */
    Optional<SysUser> getAdminByUsername(String username);

    /**
     * 登录功能
     * @param ssoLoginRequest 用户名
     * @return 生成的JWT的token
     */
    String login(AuthLoginRequest ssoLoginRequest);

    /**
     * 获取缓存服务
     */
    AdminCacheService getCacheService();

    boolean logout(AuthLogoutRequest ssoLogoutRequest);
}
