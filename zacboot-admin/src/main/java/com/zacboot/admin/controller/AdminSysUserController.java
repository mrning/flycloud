package com.zacboot.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zac.system.core.entity.admin.SysUser;
import com.zacboot.admin.beans.vos.request.UserAddRequest;
import com.zacboot.admin.beans.vos.request.UserRequest;
import com.zacboot.admin.beans.vos.request.UserRoleRequest;
import com.zacboot.admin.beans.vos.request.UserUpdateRequest;
import com.zacboot.admin.beans.vos.response.SysUserRoleResponse;
import com.zacboot.admin.beans.vos.response.SysUserResponse;
import com.zacboot.admin.service.SysUserRoleService;
import com.zacboot.admin.service.SysUserService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * AutoCreateFile 日志相关
 *
 * @author zac
 * @date 2021年4月24日星期六
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sysUser")
@Slf4j
public class AdminSysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * AutoCreateFile add
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("新增")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody UserAddRequest userAddRequest) {
        return Result.success(sysUserService.add(userAddRequest));
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
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("更新")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        return Result.success(sysUserService.update(userUpdateRequest, request.getHeader("token")));
    }

    /**
     * AutoCreateFile queryPage
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<SysUserResponse>> queryPage(@RequestBody UserRequest userRequest) {
        return Result.success(sysUserService.queryPage(userRequest));
    }

    /**
     * AutoCreateFile queryPage
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("分页查询")
    @PostMapping("/queryAll")
    public Result<List<SysUser>> queryPage() {
        return Result.success(sysUserService.queryAllUser(Wrappers.emptyWrapper()));
    }

    /**
     * 根据角色id分页查询用户列表
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("根据角色id分页查询用户列表")
    @PostMapping("/userRoleList")
    public Result<PageResult<SysUserRoleResponse>> queryUserRolePage(@RequestBody UserRoleRequest userRoleRequest) {
        return Result.success(sysUserRoleService.queryPage(userRoleRequest));
    }

    /**
     * 企微信息导入
     */
    @ApiOperation("企微用户信息导入")
    @PostMapping("/qwUserImport")
    public Result<String> qwUserImport(){
        return Result.success(sysUserService.qwUserImport(),"ok");
    }

}