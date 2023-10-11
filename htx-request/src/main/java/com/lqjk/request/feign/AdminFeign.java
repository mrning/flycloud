package com.lqjk.request.feign;

import cn.hutool.json.JSONObject;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.bizentity.SysUser;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.req.admin.LogRequest;
import com.lqjk.request.req.admin.UserRequest;
import com.lqjk.request.req.auth.AuthLoginRequest;
import com.lqjk.request.res.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "htx-admin")
public interface AdminFeign {

    @PostMapping("/sys/login")
    Result<JSONObject> adminLogin(@RequestBody AuthLoginRequest authLoginRequest,@RequestHeader(SecurityConstants.FROM) String from);

    @PostMapping("/sysLog/add")
    Result<Integer> addLog(@RequestBody LogRequest logRequest,@RequestHeader(SecurityConstants.FROM) String from);

    @PostMapping("/sysUser/query")
    Result<UserInfo> queryUser(@RequestBody UserRequest userRequest, @RequestHeader(SecurityConstants.FROM) String from);

}
