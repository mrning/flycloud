package com.lqjk.auth.service;


import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;

/**
 * Created by macro on 2019/10/18.
 */
public interface AdminService {

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
