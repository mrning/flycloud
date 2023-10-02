package com.lqjk.security.service;

import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.feign.RemoteUserFeign;
import com.lqjk.request.res.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class PigUserDetailsServiceImpl implements PigUserDetailsService {

	private final RemoteUserFeign remoteUserFeign;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {

		Map<String,String> userDTO = new HashMap<>();
		userDTO.put("username", username);
		Result<UserInfo> result = remoteUserFeign.info(userDTO, SecurityConstants.FROM_IN);
		return getUserDetails(result);
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
