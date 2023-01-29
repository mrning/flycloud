package com.zacboot.system.sso.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.system.core.request.sso.SsoLoginRequest;
import com.zac.system.core.request.sso.SsoLogoutRequest;
import com.zacboot.system.sso.beans.domain.SysUser;
import com.zacboot.system.sso.mapper.UmsAdminMapper;
import com.zacboot.system.sso.service.AdminCacheService;
import com.zacboot.system.sso.service.AdminService;
import com.zacboot.system.sso.service.BaseService;
import com.zacboot.system.sso.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.zacboot.common.base.constants.CommonConstant.*;

/**
 * 后台管理员管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<UmsAdminMapper, SysUser> implements AdminService {

    @Autowired
    private BaseService baseService;
    @Override
    public Optional<SysUser> getAdminByUsername(String username) {
        SysUser admin = getCacheService().getAdmin(username);
        if(admin!=null) return  Optional.of(admin);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUsername,username);
        List<SysUser> adminList = list(wrapper);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            getCacheService().setAdmin(admin);
            return Optional.of(admin);
        }
        return Optional.empty();
    }

    @Override
    public String login(SsoLoginRequest ssoLoginRequest) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            StpUtil.login(ssoLoginRequest.getUserUuid());
            token = StpUtil.getTokenValue();
            baseService.addLog("用户名："+ssoLoginRequest.getUsername()+"登录成功", LOG_TYPE_LOGIN,LOG_TYPE_OPERATION_2);
        } catch (Exception e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public AdminCacheService getCacheService() {
        return SpringUtil.getBean(AdminCacheService.class);
    }

    @Override
    public boolean logout(SsoLogoutRequest ssoLogoutRequest) {
        StpUtil.logoutByTokenValue(ssoLogoutRequest.getToken());
        baseService.addLog("用户id："+ssoLogoutRequest.getUsername()+"登出成功，token="+ssoLogoutRequest.getToken(),
                LOG_TYPE_LOGOUT,LOG_TYPE_OPERATION_2);
        return true;
    }
}
