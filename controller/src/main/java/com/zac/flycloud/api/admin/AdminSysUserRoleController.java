package com.zac.flycloud.api.admin;

import cn.hutool.db.PageResult;
import com.zac.flycloud.BaseController;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysUserRoleDTO;
import com.zac.flycloud.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 用户角色关联 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Api(tags = "用户角色关联")
@RestController
@RequestMapping("/api/admin/sysUserRole")
@Slf4j
public class AdminSysUserRoleController extends BaseController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * AutoCreateFile add
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public DataResponseResult<Integer> add(@RequestBody SysUserRoleDTO sysUserRoleDTO) {
        return DataResponseResult.success(sysUserRoleService.add(sysUserRoleDTO));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public DataResponseResult<Integer> del(@RequestBody SysUserRoleDTO sysUserRoleDTO) {
        return DataResponseResult.success(sysUserRoleService.del(sysUserRoleDTO));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public DataResponseResult<Integer> update(@RequestBody SysUserRoleDTO sysUserRoleDTO) {
        return DataResponseResult.success(sysUserRoleService.update(sysUserRoleDTO));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public DataResponseResult<PageResult<SysUserRoleDTO>> queryPage(@RequestBody SysUserRoleDTO sysUserRoleDTO) {
        return DataResponseResult.success(sysUserRoleService.queryPage(sysUserRoleDTO));
    }
}