package com.lqjk.auth.service.impl;

import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import org.springframework.stereotype.Service;

@Service
public abstract class CommonServiceImpl implements ClientCommonService {

    @Override
    public ClientCommonService getService(String clientId) {
        return null;
    }

    @Override
    public Result<String> login(AuthLoginRequest ssoLoginRequest) {
        return null;
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
