package com.zacboot.system.sso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zac.system.core.request.sso.SsoLoginRequest;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.sso.domain.SysUser;
import com.zacboot.system.sso.dto.AdminParam;
import com.zacboot.system.sso.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 * Created by macro on 2020/7/17.
 */
@RestController
@Api(tags = "AuthController", description = "认证中心登录认证")
@RequestMapping("/sso")
public class AuthController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result<SysUser> register(@Validated @RequestBody AdminParam adminParam) {
        SysUser umsAdmin = adminService.register(adminParam);
        if (umsAdmin == null) {
            return Result.error(-1,"用户不存在",null);
        }
        return Result.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public String login(@Validated @RequestBody SsoLoginRequest ssoLoginRequest) {
        return adminService.login(ssoLoginRequest.getUsername(), ssoLoginRequest.getPassword());
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public Result refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return Result.error("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return Result.success(tokenMap);
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout() {
        return Result.success();
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<SysUser>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysUser> adminList = adminService.list(keyword, pageSize, pageNum);
        return Result.success(adminList);
    }
}
