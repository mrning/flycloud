package com.zacboot.common.base.constants;

public interface RedisKey {
    // 登录后，将用户信息存到缓存
    String LOGIN_TOKEN = "system:userinfo";
}
