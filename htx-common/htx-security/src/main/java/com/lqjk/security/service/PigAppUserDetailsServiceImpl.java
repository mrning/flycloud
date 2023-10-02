package com.lqjk.security.service;

import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.CacheConstants;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.request.feign.RemoteUserFeign;
import com.lqjk.request.res.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@RequiredArgsConstructor
public class PigAppUserDetailsServiceImpl implements PigUserDetailsService {

	private final RemoteUserFeign remoteUserFeign;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		Map<String,String> userDTO = new HashMap<>();
		userDTO.put("phone",phone);
        return getUserDetails(remoteUserFeign.info(userDTO, SecurityConstants.FROM_IN));
	}

	/**
	 * check-token 使用
	 * @param pigUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(PigUser pigUser) {
		return this.loadUserByUsername(pigUser.getPhone());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.MOBILE.equals(grantType);
	}

}
