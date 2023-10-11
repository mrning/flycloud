package com.lqjk.security.service;

import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.feign.AdminFeign;
import com.lqjk.request.req.admin.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class HtxUserDetailsServiceImpl implements HtxUserDetailsService {

	private final AdminFeign adminFeign;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {

		UserRequest userRequest = new UserRequest();
		userRequest.setUsername(username);
		return getUserDetails(adminFeign.queryUser(userRequest, SecurityConstants.FROM_IN));
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
