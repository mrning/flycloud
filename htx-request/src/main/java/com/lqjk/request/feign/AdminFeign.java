package com.lqjk.request.feign;

import cn.hutool.json.JSONObject;
import com.lqjk.base.constants.SecurityConstants;
import com.lqjk.request.FeignResult;
import com.lqjk.request.req.admin.LogRequest;
import com.lqjk.request.req.auth.AuthLoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "htx-admin")
public interface AdminFeign {

    @PostMapping("/sys/login")
    FeignResult<JSONObject> adminLogin(@RequestBody AuthLoginRequest authLoginRequest, @RequestHeader(SecurityConstants.FROM) String from);

    @PostMapping("/sysLog/add")
    FeignResult<Integer> addLog(@RequestBody LogRequest logRequest,@RequestHeader(SecurityConstants.FROM) String from);

    @PostMapping("/sys/checkNews")
    FeignResult<String> checkNews();
}
