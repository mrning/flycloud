package com.zacboot.admin.feign;

import com.zacboot.system.core.request.sso.SsoLoginRequest;
import com.zacboot.system.core.request.sso.SsoLogoutRequest;
import com.zacboot.common.base.basebeans.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "zacboot-system-sso", path = "/sso")
public interface SsoServiceFeign {

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    String login(@Validated @RequestBody SsoLoginRequest ssoLoginRequest);

    @PostMapping(value = "/logout")
    Result<Boolean> logout(@RequestBody SsoLogoutRequest ssoLogoutRequest);
}
