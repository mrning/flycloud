package com.zac.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.zac.auth.service.ClientCommonService;
import com.zac.base.basebeans.Result;
import com.zac.base.domain.UserDTO;
import com.zac.request.req.auth.AuthLoginRequest;
import com.zac.request.req.auth.AuthLogoutRequest;
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
