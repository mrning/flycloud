package com.zac.request.feign;

import cn.hutool.json.JSONObject;
import com.zac.base.constants.SecurityConstants;
import com.zac.request.FeignResult;
import com.zac.request.req.admin.LogRequest;
import com.zac.request.req.auth.AuthLoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "zacboot-admin")
public interface AdminFeign {

    @PostMapping("/sys/login")
    FeignResult<JSONObject> adminLogin(@RequestBody AuthLoginRequest authLoginRequest, @RequestHeader(SecurityConstants.FROM) String from);

    @PostMapping("/sysLog/add")
    FeignResult<Integer> addLog(@RequestBody LogRequest logRequest,@RequestHeader(SecurityConstants.FROM) String from);

    @PostMapping("/sys/checkNews")
    FeignResult<String> checkNews();
}
