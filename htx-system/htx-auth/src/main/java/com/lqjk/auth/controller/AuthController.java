package com.lqjk.auth.controller;

import com.lqjk.auth.service.AdminService;
import com.lqjk.base.basebeans.Result;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.req.auth.AuthLogoutRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 自定义Oauth2获取令牌接口
 * Created by macro on 2020/7/17.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/login")
    public String login(@Validated @RequestBody AuthLoginRequest ssoLoginRequest) {
        return adminService.login(ssoLoginRequest);
    }

    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> refreshToken(HttpServletRequest request) {
        return Result.success(request.getHeader("satoken"));
    }

    @PostMapping(value = "/logout")
    public Result<Boolean> logout(@RequestBody AuthLogoutRequest ssoLogoutRequest) {
        return Result.success(adminService.logout(ssoLogoutRequest));
    }
}
