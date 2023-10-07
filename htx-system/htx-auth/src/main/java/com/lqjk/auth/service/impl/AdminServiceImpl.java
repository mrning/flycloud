package com.lqjk.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lqjk.auth.constant.ClientContent;
import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.request.feign.AdminFeign;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 后台管理员管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Primary
@Service("AdminService")
public class AdminServiceImpl extends CommonServiceImpl {

    @Autowired
    private RedisUtil redisUtil;
    @Value("${spring.redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${spring.redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Autowired
    private AdminFeign adminFeign;

    @Override
    public ClientCommonService getService(String clientId) {
        return clientId.equals(ClientContent.CLIENT_ADMIN.getClientId()) ? this : null;
    }

    @Override
    public String login(AuthLoginRequest ssoLoginRequest) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            Result<JSONObject> res = adminFeign.adminLogin(ssoLoginRequest);
            if (res.isSuccess()){
                JSONObject resObj = JSONUtil.parseObj(res.getResult().get("userInfo"));
                StpUtil.login(resObj.getStr("uuid"));
                token = StpUtil.getTokenValue();
            }
        } catch (Exception e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public boolean logout(AuthLogoutRequest ssoLogoutRequest) {
        StpUtil.logoutByTokenValue(ssoLogoutRequest.getToken());
        return true;
    }

    @Override
    public UserDTO getAdminCacheUser(String username) {
        String key = "admin:" + REDIS_KEY_ADMIN + ":" + username;
        return (UserDTO) redisUtil.get(key);
    }

    @Override
    public void setAdminCacheUser(UserDTO admin) {
        String key = "admin:" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisUtil.set(key, admin, REDIS_EXPIRE);
    }
}
