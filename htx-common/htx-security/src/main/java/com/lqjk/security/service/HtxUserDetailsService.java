package com.lqjk.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.res.UserInfo;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

/**
 * @author lengleng
 * @date 2021/12/21
 */
public interface HtxUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(Result<UserInfo> result) {
		UserInfo info = result.getResult();

		Set<String> dbAuthsSet = new HashSet<>();

		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(new List[]{List.of(info.getRoles())}).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));

		}

		Collection<GrantedAuthority> authorities = AuthorityUtils
			.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		// 构造security用户
		return new HtxSecurityUser(info.getSysUser().getId(), info.getSysUser().getUsername(), info.getSysUser().getPassword(),
				info.getSysUser().getPhone(),true, true, true, true, authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param htxSecurityUser user
	 * @return
	 */
	default UserDetails loadUserByUser(HtxSecurityUser htxSecurityUser) {
		return this.loadUserByUsername(htxSecurityUser.getUsername());
	}

}
