package com.zacboot.system.sso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zacboot.system.core.request.sso.SsoLoginRequest;
import com.zacboot.system.core.request.sso.SsoLogoutRequest;
import com.zacboot.system.sso.beans.domain.SysUser;

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
    String login(SsoLoginRequest ssoLoginRequest);

    /**
     * 获取缓存服务
     */
    AdminCacheService getCacheService();

    boolean logout(SsoLogoutRequest ssoLogoutRequest);
}
