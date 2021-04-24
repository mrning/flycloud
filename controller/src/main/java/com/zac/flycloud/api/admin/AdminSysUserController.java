package com.zac.flycloud.api.admin;

import cn.hutool.db.PageResult;
import com.zac.flycloud.BaseController;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@RestController
@RequestMapping("/api/admin/sysUser")
@Slf4j
public class AdminSysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/add")
    public DataResponseResult<Integer> add(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.add(sysUserDTO));
    }

    @PostMapping("/del")
    public DataResponseResult<Integer> del(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.del(sysUserDTO));
    }

    @PostMapping("/update")
    public DataResponseResult<Integer> update(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.update(sysUserDTO));
    }

    @PostMapping("/queryPage")
    public DataResponseResult<PageResult<SysUserDTO>> queryPage(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.queryPage(sysUserDTO));
    }
}