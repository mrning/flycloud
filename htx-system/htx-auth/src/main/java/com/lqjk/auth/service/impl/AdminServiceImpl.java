package com.lqjk.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lqjk.auth.service.AdminCacheService;
import com.lqjk.auth.service.AdminService;
import com.lqjk.base.utils.SpringContextUtils;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 后台管理员管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public String login(AuthLoginRequest ssoLoginRequest) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            StpUtil.login(ssoLoginRequest.getUserUuid());
            token = StpUtil.getTokenValue();
        } catch (Exception e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public AdminCacheService getCacheService() {
        return SpringContextUtils.getBean(AdminCacheService.class);
    }

    @Override
    public boolean logout(AuthLogoutRequest ssoLogoutRequest) {
        StpUtil.logoutByTokenValue(ssoLogoutRequest.getToken());
        return true;
    }
}
