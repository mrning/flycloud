package com.zac.auth.service;


import com.zac.base.basebeans.Result;
import com.zac.base.domain.UserDTO;
import com.zac.request.req.auth.AuthLoginRequest;
import com.zac.request.req.auth.AuthLogoutRequest;

/**
 * 客户端公共接口
 */
public interface ClientCommonService {
    ClientCommonService getService();


    /**
     * 登录功能
     *
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
