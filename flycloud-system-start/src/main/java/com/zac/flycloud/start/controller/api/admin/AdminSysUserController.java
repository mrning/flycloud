package com.zac.flycloud.start.controller.api.admin;

import com.zac.flycloud.start.bean.basebean.PageResult;
import com.zac.flycloud.start.bean.basebean.Result;
import com.zac.flycloud.start.bean.entity.SysUser;
import com.zac.flycloud.start.bean.vos.request.UserRequest;
import com.zac.flycloud.start.bean.vos.request.UserRoleRequest;
import com.zac.flycloud.start.bean.vos.response.SysUserRoleResponse;
import com.zac.flycloud.start.controller.BaseController;
import com.zac.flycloud.start.service.SysUserRoleService;
import com.zac.flycloud.start.service.SysUserService;
import com.zac.flycloud.start.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * AutoCreateFile add
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("新增")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysUser sysUser) {
        sysUser.setPassword(PasswordUtil.getPasswordEncode(sysUser.getPassword()));
        return Result.success(sysUserService.add(sysUser));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("删除")
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysUser sysUser) {
        return Result.success(sysUserService.del(sysUser));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("更新")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysUser sysUser) {
        return Result.success(sysUserService.update(sysUser));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<SysUser>> queryPage(@RequestBody UserRequest userRequest) {
        return Result.success(sysUserService.queryPage(userRequest));
    }

    /**
     * 根据角色id分页查询用户列表
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("根据角色id分页查询用户列表")
    @PostMapping("/userRoleList")
    public Result<PageResult<SysUserRoleResponse>> queryUserRolePage(@RequestBody UserRoleRequest userRoleRequest) {
        return Result.success(sysUserRoleService.queryPage(userRoleRequest));
    }


}