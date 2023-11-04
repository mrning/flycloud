package com.zac.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zac.auth.service.ClientCommonService;
import com.zac.base.constants.RedisKey;
import com.zac.base.constants.SecurityConstants;
import com.zac.base.domain.UserDTO;
import com.zac.base.enums.UserClientEnum;
import com.zac.base.utils.RedisUtil;
import com.zac.request.FeignResult;
import com.zac.request.feign.AdminFeign;
import com.zac.request.req.auth.AuthLoginRequest;
import com.zac.request.req.auth.AuthLogoutRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Long REDIS_EXPIRE_TOKEN_ADMIN = 60*60*24L;

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
                log.info("adminFeign.adminLogin res {}", res.getResult());
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
        String key = RedisKey.LOGIN_SYSTEM_USERINFO + username;
        return (UserDTO) redisUtil.get(key);
    }

    @Override
    public void setAdminCacheUser(UserDTO admin) {
        String key = RedisKey.LOGIN_SYSTEM_USERINFO + admin.getUsername();
        redisUtil.set(key, admin, 60*60*24L);
    }
}
