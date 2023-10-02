package com.lqjk.auth.service;


import com.lqjk.base.domain.UserDTO;

/**
 * 后台用户缓存管理Service
 * Created by macro on 2020/3/13.
 */
public interface AdminCacheService {
    /**
     * 获取缓存后台用户信息
     */
    UserDTO getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UserDTO admin);
}
