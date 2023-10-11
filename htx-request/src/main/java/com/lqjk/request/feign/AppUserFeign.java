package com.lqjk.request.feign;

import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.req.admin.UserRequest;
import com.lqjk.request.res.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "htx-user")
public interface AppUserFeign {

    @PostMapping("/appUser/query")
    Result<UserInfo> queryUser(@RequestBody UserRequest userRequest, @RequestHeader(SecurityConstants.FROM) String from);
}
