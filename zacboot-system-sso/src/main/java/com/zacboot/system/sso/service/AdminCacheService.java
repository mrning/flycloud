package com.zacboot.system.sso.service;


import com.zacboot.system.sso.domain.SysUser;

/**
 * 后台用户缓存管理Service
 * Created by macro on 2020/3/13.
 */
public interface AdminCacheService {
    /**
     * 获取缓存后台用户信息
     */
    SysUser getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(SysUser admin);
}
