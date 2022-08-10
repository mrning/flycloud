package com.zacboot.admin.feign;

import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.sso.dto.UmsAdminLoginParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "zacboot-sys-sso", path = "/sso")
public interface SsoServiceFeign {

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    Result login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam);
}
