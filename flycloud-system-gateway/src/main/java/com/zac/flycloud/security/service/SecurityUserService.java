package com.zac.flycloud.security.service;

import com.zac.flycloud.security.bean.SysRole;
import com.zac.flycloud.security.bean.SysUser;
import com.zac.flycloud.security.dao.SysUserDao;
import com.zac.flycloud.security.dao.SysUserRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("securityUserService")
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private SysUserDao userDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        SysUser user = userDao.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            // 查询用户权限
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<SysRole> roles = sysUserRoleDao.getRolesByUserUuid(user.getUuid());
            if (!roles.isEmpty()) {
                roles.stream().forEach(sysRole -> authorities.add(new SimpleGrantedAuthority(sysRole.getRoleCode())));
            } else {
                log.error("SecurityUserService #loadUserByUsername 用户未关联角色，username = " + username);
            }

            //  accountNonExpired = 账户是否过期;
            //  accountNonLocked = 账户是否锁定;
            //  credentialsNonExpired = 登录凭据(密碼)是否过期
            return new User(user.getUsername(), user.getPassword(), user.getDeleted(), true, true, true, authorities);
        }
    }
}
