package com.zac.flycloud.service;

import com.zac.flycloud.dao.UserDao;
import com.zac.flycloud.entity.tablemodel.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        SysUser user = userDao.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }else{
            // 查询用户权限
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            //  accountNonExpired = 账户是否过期; accountNonLocked = 账户是否锁定; credentialsNonExpired = 登录凭据(密碼)是否过期
            return new User(user.getUsername(),user.getPassword(),user.getEnableStatus(),true,true,true,authorities);
        }
    }
}
