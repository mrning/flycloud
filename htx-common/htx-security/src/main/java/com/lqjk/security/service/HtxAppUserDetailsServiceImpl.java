package com.lqjk.security.service;

import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.feign.AppUserFeign;
import com.lqjk.request.req.admin.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@RequiredArgsConstructor
public class HtxAppUserDetailsServiceImpl implements HtxUserDetailsService {

    private final AppUserFeign appUserFeign;

    /**
     * 手机号登录
     *
     * @param phone 手机号
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String phone) {
        UserRequest userRequest = new UserRequest();
        userRequest.setPhone(phone);
        return getUserDetails(appUserFeign.queryUser(userRequest, SecurityConstants.FROM_IN));
    }

    /**
     * check-token 使用
     *
     * @param htxSecurityUser user
     * @return
     */
    @Override
    public UserDetails loadUserByUser(HtxSecurityUser htxSecurityUser) {
        return this.loadUserByUsername(htxSecurityUser.getPhone());
    }

    /**
     * 是否支持此客户端校验
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {
        return SecurityConstants.MOBILE.equals(grantType);
    }

}
