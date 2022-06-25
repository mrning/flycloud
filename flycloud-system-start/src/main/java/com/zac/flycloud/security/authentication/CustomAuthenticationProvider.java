package com.zac.flycloud.security.authentication;

import com.zac.flycloud.bean.dto.SysUser;
import com.zac.flycloud.security.service.SecurityUserService;
import com.zac.flycloud.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 登录请求第三步拦截
 * 自定义认证处理
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    SecurityUserService securityUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = securityUserService.loadUserByUsername(userName);

        SysUser userInfo = new SysUser();
        userInfo.setUsername(userDetails.getUsername());
        userInfo.setPassword(userDetails.getPassword());
        userInfo.setDeleted(!userDetails.isEnabled());

        boolean isValid = PasswordUtil.getPasswordMatch(password, userInfo.getPassword());
        // 验证密码
        if (!isValid) {
            throw new BadCredentialsException("密码错误！");
        }
        return new UsernamePasswordAuthenticationToken(userInfo, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
