package com.zacboot.system.sso.service.impl;

import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.system.sso.beans.domain.SysUser;
import com.zacboot.system.sso.service.AdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 后台用户缓存管理Service实现类
 * Created by macro on 2020/3/13.
 */
@Service
public class AdminCacheServiceImpl implements AdminCacheService {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${spring.redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${spring.redis.key.admin}")
    private String REDIS_KEY_ADMIN;



    @Override
    public SysUser getAdmin(String username) {
        String key = "admin:" + REDIS_KEY_ADMIN + ":" + username;
        return (SysUser) redisUtil.get(key);
    }

    @Override
    public void setAdmin(SysUser admin) {
        String key = "admin:" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisUtil.set(key, admin, REDIS_EXPIRE);
    }
}
