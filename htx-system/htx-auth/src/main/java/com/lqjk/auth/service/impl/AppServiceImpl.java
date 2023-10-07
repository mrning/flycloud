package com.lqjk.auth.service.impl;

import com.lqjk.auth.constant.ClientContent;
import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import org.springframework.stereotype.Service;

@Service("AppService")
public class AppServiceImpl extends CommonServiceImpl {

    @Override
    public ClientCommonService getService(String clientId) {
        return clientId.equals(ClientContent.CLIENT_APP.getClientId()) ? this : null;
    }

    @Override
    public String login(AuthLoginRequest ssoLoginRequest) {
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
