package com.zacboot.system.sso.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zacboot.system.sso.domain.SysUser;
import com.zacboot.system.sso.dto.AdminParam;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Created by macro on 2019/10/18.
 */
public interface AdminService extends IService<SysUser> {

    /**
     * 根据用户名获取后台管理员
     */
    Optional<SysUser> getAdminByUsername(String username);

    /**
     * 注册功能
     */
    SysUser register(AdminParam adminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     */
    Page<SysUser> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取缓存服务
     */
    AdminCacheService getCacheService();
}
