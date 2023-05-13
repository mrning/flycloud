package com.zacboot.admin.work.feign;

import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.request.admin.UserRequest;
import com.zacboot.system.core.response.admin.SysUserDeptAndRoleInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("zacboot-admin")
public interface AdminFeign {

    @PostMapping("/sysUser/deptAndRoleInfo")
    public Result<SysUserDeptAndRoleInfo> deptAndRoleInfo(@RequestBody UserRequest userRequest);
}
