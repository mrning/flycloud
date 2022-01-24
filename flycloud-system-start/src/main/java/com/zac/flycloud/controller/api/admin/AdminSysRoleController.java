package com.zac.flycloud.controller.api.admin;

import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.basebean.Result;
import com.zac.flycloud.bean.dto.SysRole;
import com.zac.flycloud.bean.vos.RoleRequestVO;
import com.zac.flycloud.controller.BaseController;
import com.zac.flycloud.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AutoCreateFile 角色管理 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/admin/sysRole")
@Slf4j
public class AdminSysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * AutoCreateFile add
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody SysRole sysRole) {
        return Result.success(sysRoleService.add(sysRole));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody SysRole sysRole) {
        return Result.success(sysRoleService.del(sysRole));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody SysRole sysRole) {
        return Result.success(sysRoleService.update(sysRole));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<SysRole>> queryPage(@RequestBody RoleRequestVO roleRequestVO) {
        return Result.success(sysRoleService.queryPage(roleRequestVO));
    }

    /**
     * 查询全部角色
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryAll")
    @ApiOperation("查询全部")
    public Result<List<SysRole>> queryAll() {
        return Result.success(sysRoleService.queryAll());
    }
}