package com.zacboot.system.sso.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zacboot.system.sso.domain.AdminLoginLog;
import com.zacboot.system.sso.domain.AdminUserDetails;
import com.zacboot.system.sso.domain.SysUser;
import com.zacboot.system.sso.dto.AdminParam;
import com.zacboot.system.sso.mapper.UmsAdminMapper;
import com.zacboot.system.sso.service.AdminService;
import com.zacboot.system.sso.service.AdminCacheService;
import com.zacboot.system.sso.utils.JwtTokenUtil;
import com.zacboot.system.sso.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 后台管理员管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AdminServiceImpl extends ServiceImpl<UmsAdminMapper, SysUser> implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public SysUser register(AdminParam adminParam) {
        SysUser umsAdmin = new SysUser();
        BeanUtils.copyProperties(adminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setDeleted(Boolean.FALSE);
        //查询是否有相同用户名的用户
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUsername,umsAdmin.getUsername());
        List<SysUser> umsAdminList = list(wrapper);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        baseMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            Assert.isTrue(!passwordEncoder.matches(password,userDetails.getPassword()),"密码不正确");
            Assert.isTrue(!userDetails.isEnabled(),"帐号已被禁用");
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        Optional<SysUser> admin = getAdminByUsername(username);
        if(!admin.isPresent()) return;
        AdminLoginLog loginLog = new AdminLoginLog();
        loginLog.setAdminId(admin.orElseThrow().getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public Page<SysUser> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<SysUser> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SysUser> lambda = wrapper.lambda();
        if(StrUtil.isNotEmpty(keyword)){
            lambda.like(SysUser::getUsername,keyword);
            lambda.or().like(SysUser::getNickname,keyword);
        }
        return page(page,wrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        SysUser admin = getAdminByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
        return new AdminUserDetails(admin);
//        if (admin != null) {
//            List<UmsResource> resourceList = getResourceList(admin.getId());
//            return new AdminUserDetails(admin,resourceList);
//        }

    }

    @Override
    public AdminCacheService getCacheService() {
        return SpringUtil.getBean(AdminCacheService.class);
    }
}
