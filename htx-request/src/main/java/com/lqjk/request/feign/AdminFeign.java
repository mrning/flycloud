package com.lqjk.request.feign;

import com.lqjk.base.basebeans.Result;
import com.lqjk.base.domain.UserDTO;
import com.lqjk.request.req.admin.LogRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by macro on 2019/10/18.
 */
@FeignClient("htx-admin")
public interface AdminFeign {

    @GetMapping("/admin/loadByUsername")
    UserDTO loadUserByUsername(@RequestParam String username);

    @PostMapping("/admin/sysLog/add")
    Result<Integer> addLog(@RequestBody LogRequest logRequest);

}
