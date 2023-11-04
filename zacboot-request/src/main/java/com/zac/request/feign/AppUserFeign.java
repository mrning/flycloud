package com.zac.request.feign;

import com.zac.base.basebeans.Result;
import com.zac.base.constants.SecurityConstants;
import com.zac.request.FeignResult;
import com.zac.request.req.admin.UserRequest;
import com.zac.request.res.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "zacboot-user")
public interface AppUserFeign {

    @PostMapping("/appUser/query")
    FeignResult<UserInfo> queryUser(@RequestBody UserRequest userRequest, @RequestHeader(SecurityConstants.FROM) String from);
}
