package com.lqjk.request.feign;

import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.res.SysOauthClientDetails;
import com.lqjk.request.res.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient("htx-user")
public interface RemoteUserFeign {

	/**
	 * 通过用户名查询用户、角色信息
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/user/info/query")
	Result<UserInfo> info(@SpringQueryMap Map<String,String> map, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 锁定用户
	 * @param username 用户名
	 * @param from 调用标识
	 * @return
	 */
	@PutMapping("/user/lock/{username}")
	Result<Boolean> lockUser(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/client/getClientDetailsById/{clientId}")
	Result<SysOauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId,
													   @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 查询全部客户端
	 * @param from 调用标识
	 * @return R
	 */
	@GetMapping("/client/list")
	Result<List<SysOauthClientDetails>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);

}
