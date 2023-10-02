package com.lqjk.auth.service.impl;

import com.lqjk.base.domain.UserDTO;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.auth.service.AdminCacheService;
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
    public UserDTO getAdmin(String username) {
        String key = "admin:" + REDIS_KEY_ADMIN + ":" + username;
        return (UserDTO) redisUtil.get(key);
    }

    @Override
    public void setAdmin(UserDTO admin) {
        String key = "admin:" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisUtil.set(key, admin, REDIS_EXPIRE);
    }
}
