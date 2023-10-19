package com.lqjk.auth.controller;

import com.lqjk.auth.service.impl.MultiBeanFactory;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import com.lqjk.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 自定义获取令牌接口
 */
@Tag(name = "统一认证鉴权")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Inner
    @Operation(summary = "多端登录共用")
    @PostMapping(value = "/token")
    public Result<String> getToken(@Validated @RequestBody AuthLoginRequest ssoLoginRequest, @RequestHeader(SecurityConstants.CLIENT_ID) String clientId) {
        return MultiBeanFactory.getService(clientId).login(ssoLoginRequest);
    }

    @Inner
    @Operation(summary = "刷新token")
    @GetMapping(value = "/refreshToken")
    public Result<Object> refreshToken(HttpServletRequest request) {
        return Result.success(request.getHeader("satoken"));
    }

    @Operation(summary = "登出")
    @PostMapping(value = "/logout")
    public Result<Boolean> logout(@RequestBody AuthLogoutRequest ssoLogoutRequest, @RequestHeader(SecurityConstants.CLIENT_ID) String clientId) {
        return Result.success(MultiBeanFactory.getService(clientId).logout(ssoLogoutRequest));
    }
}
