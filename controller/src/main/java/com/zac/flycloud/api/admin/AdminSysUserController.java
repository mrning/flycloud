package com.zac.flycloud.api.admin;

import cn.hutool.db.PageResult;
import com.zac.flycloud.BaseController;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


/**
 * AutoCreateFile 日志相关 
 * @date 2021年4月24日星期六
 * @author zac
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/admin/sysUser")
@Slf4j
public class AdminSysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * AutoCreateFile add
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("新增")
    @PostMapping("/add")
    public DataResponseResult<Integer> add(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.add(sysUserDTO));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("删除")
    @PostMapping("/del")
    public DataResponseResult<Integer> del(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.del(sysUserDTO));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("更新")
    @PostMapping("/update")
    public DataResponseResult<Integer> update(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.update(sysUserDTO));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("分页查询")
    @PostMapping("/queryPage")
    public DataResponseResult<PageResult<SysUserDTO>> queryPage(@RequestBody SysUserDTO sysUserDTO) {
        return DataResponseResult.success(sysUserService.queryPage(sysUserDTO));
    }
}