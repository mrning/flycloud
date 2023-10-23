package com.lqjk.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.RedisKey;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.base.enums.UserClientEnum;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.request.FeignResult;
import com.lqjk.request.feign.AdminFeign;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 后台管理员管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Service("AdminService")
public class AdminServiceImpl implements ClientCommonService {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.redis.expire.common}")
    private Long REDIS_EXPIRE;

    @Value("${spring.redis.expire.token.admin:0L}")
    private Long REDIS_EXPIRE_TOKEN_ADMIN;

    @Value("${spring.redis.key.admin}")
    private String REDIS_KEY_ADMIN;

    @Autowired
    private AdminFeign adminFeign;

    @Override
    public ClientCommonService getService() {
        return this;
    }

    @Override
    public String login(AuthLoginRequest authLoginRequest) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            FeignResult<JSONObject> res = adminFeign.adminLogin(authLoginRequest, SecurityConstants.FROM_IN);
            if (res.isSuccess()) {
                JSONObject resObj = JSONUtil.parseObj(res.getResult().get("userInfo"));
                StpUtil.login(resObj.getStr("uuid"));
                token = StpUtil.getTokenValue();
                // 登录后将用户信息缓存
                String key = UserClientEnum.ADMIN.getValue() + ":" + RedisKey.LOGIN_SYSTEM_USERINFO + token;
                redisUtil.set(key, res.getResult(), REDIS_EXPIRE_TOKEN_ADMIN, TimeUnit.HOURS);
                return token;
            }
        } catch (Exception e) {
            log.error("", e);
            return "登录异常";
        }
        return null;
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
