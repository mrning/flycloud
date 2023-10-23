package com.lqjk.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import org.springframework.stereotype.Service;

@Service("AppService")
public class AppServiceImpl implements ClientCommonService {

    @Override
    public ClientCommonService getService() {
        return this;
    }

    @Override
    public String login(AuthLoginRequest ssoLoginRequest) {
        // TODO app登录逻辑
        StpUtil.login(ssoLoginRequest.getUsername());
        return StpUtil.getTokenValue();
    }

    @Override
    public boolean logout(AuthLogoutRequest ssoLogoutRequest) {
        return false;
    }

    @Override
    public UserDTO getAdminCacheUser(String username) {
        return null;
    }

    @Override
    public void setAdminCacheUser(UserDTO admin) {

    }
}
