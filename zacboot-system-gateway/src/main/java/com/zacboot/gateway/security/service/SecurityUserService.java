package com.zacboot.gateway.security.service;

import com.zacboot.gateway.security.bean.SysRole;
import com.zacboot.gateway.security.bean.SysUser;
import com.zacboot.gateway.security.dao.SysUserRoleDao;
import com.zacboot.gateway.security.dao.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("securityUserService")
@RequiredArgsConstructor
public class SecurityUserService implements ReactiveUserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        // 根据用户名查询用户
        SysUser user = sysUserMapper.getUserByName(username);
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

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            UserDetails userDetails = User.builder()
                    .username(username)
                    .password(user.getPassword())
                    .passwordEncoder(bCryptPasswordEncoder::encode)
                    .roles(roles.stream().map(SysRole::getRoleCode).collect(Collectors.toList()).toArray(new String[]{}))
                    .build();

            MapReactiveUserDetailsService mapReactiveUserDetailsService = new MapReactiveUserDetailsService(userDetails);
            mapReactiveUserDetailsService.findByUsername(username);
            return Mono.just(User.withUserDetails(userDetails).build());
        }
    }
}
