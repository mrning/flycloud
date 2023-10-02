package com.lqjk.base.constants;

public interface RedisKey {
    // 登录后，将用户信息存到缓存
    String LOGIN_TOKEN = "system:userinfo";

    String PDD_DDK_TOKEN = "pdd:ddkapi:token";
}
