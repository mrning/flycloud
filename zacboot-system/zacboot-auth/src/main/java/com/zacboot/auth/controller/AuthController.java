package com.zacboot.auth.controller;

import com.zacboot.auth.service.AdminService;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.core.request.auth.AuthLoginRequest;
import com.zacboot.core.request.auth.AuthLogoutRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Oauth2获取令牌接口
 * Created by macro on 2020/7/17.
 */
@RestController
@Api(tags = "AuthController", description = "认证中心登录认证")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public String login(@Validated @RequestBody AuthLoginRequest ssoLoginRequest) {
        return adminService.login(ssoLoginRequest);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> refreshToken(HttpServletRequest request) {
        return Result.success(request.getHeader("satoken"));
    }

    @ApiOperation(value = "登出功能")
    @PostMapping(value = "/logout")
    public Result<Boolean> logout(@RequestBody AuthLogoutRequest ssoLogoutRequest) {
        return Result.success(adminService.logout(ssoLogoutRequest));
    }
}
