package com.zac.flycloud.service.feignservice;

import cn.hutool.db.PageResult;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "fly-cloud")
public interface FSysUserService {

    @PostMapping("/api/admin/sysUser/queryPage")
    DataResponseResult<PageResult<SysUserDTO>> queryPage(@RequestBody SysUserDTO sysUserDTO);
}
