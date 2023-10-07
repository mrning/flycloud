package com.lqjk.auth.service;


import com.lqjk.auth.constant.ClientContent;
import com.lqjk.auth.service.impl.AdminServiceImpl;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;

import java.util.IllegalFormatCodePointException;

/**
 * 客户端公共接口
 */
public interface ClientCommonService {
    ClientCommonService getService(String clientId);


    /**
     * 登录功能
     * @param ssoLoginRequest 用户名
     * @return 生成的JWT的token
     */
    String login(AuthLoginRequest ssoLoginRequest);

    boolean logout(AuthLogoutRequest ssoLogoutRequest);

    /**
     * 获取缓存后台用户信息
     */
    UserDTO getAdminCacheUser(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdminCacheUser(UserDTO admin);
}
