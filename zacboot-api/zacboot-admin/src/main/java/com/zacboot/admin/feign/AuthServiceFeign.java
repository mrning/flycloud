package com.zacboot.admin.feign;

import com.zacboot.common.base.basebeans.Result;
import com.zacboot.core.request.auth.AuthLoginRequest;
import com.zacboot.core.request.auth.AuthLogoutRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "zacboot-auth", path = "/auth")
public interface AuthServiceFeign {

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    String login(@Validated @RequestBody AuthLoginRequest ssoLoginRequest);

    @PostMapping(value = "/logout")
    Result<Boolean> logout(@RequestBody AuthLogoutRequest ssoLogoutRequest);
}
