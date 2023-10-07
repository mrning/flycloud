package com.lqjk.auth.controller;

import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.auth.service.impl.CommonServiceImpl;
import com.lqjk.base.basebeans.Result;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 自定义获取令牌接口
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientCommonService commonService;

    @PostMapping(value = "/login")
    public Result<?> login(@Validated @RequestBody AuthLoginRequest ssoLoginRequest) {
        return Result.success(commonService.getService(ssoLoginRequest.getClientId()).login(ssoLoginRequest));
    }

    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> refreshToken(HttpServletRequest request) {
        return Result.success(request.getHeader("satoken"));
    }

    @PostMapping(value = "/logout")
    public Result<Boolean> logout(@RequestBody AuthLogoutRequest ssoLogoutRequest) {
        return Result.success(commonService.getService(ssoLogoutRequest.getClientId()).logout(ssoLogoutRequest));
    }
}
