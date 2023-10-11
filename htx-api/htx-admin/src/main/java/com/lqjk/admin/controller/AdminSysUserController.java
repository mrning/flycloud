package com.lqjk.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lqjk.admin.beans.vos.request.UserDeptRequest;
import com.lqjk.base.bizentity.SysUser;
import com.lqjk.admin.service.SysUserDeptService;
import com.lqjk.admin.beans.vos.request.UserAddRequest;
import com.lqjk.admin.beans.vos.request.UserRoleRequest;
import com.lqjk.admin.beans.vos.request.UserUpdateRequest;
import com.lqjk.admin.beans.vos.response.SysUserRoleResponse;
import com.lqjk.admin.beans.vos.response.SysUserResponse;
import com.lqjk.admin.service.SysUserRoleService;
import com.lqjk.admin.service.SysUserService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.basebeans.Result;
import com.lqjk.request.req.admin.UserRequest;
import com.lqjk.request.res.SysUserDeptAndRoleInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * AutoCreateFile 日志相关
 *
 * @author zac
 * @date 2021年4月24日星期六
 */
@Slf4j
@Tag(name = "用户管理")
@RestController
@RequestMapping("/sysUser")
public class AdminSysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserDeptService sysUserDeptService;

    /**
     * AutoCreateFile add
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody @Validated UserAddRequest userAddRequest) {
        return Result.success(sysUserService.add(userAddRequest));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月24日星期六
     * @author zac
     */
    @Operation(summary = "删除用户")
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
    @Operation(summary = "修改用户")
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
    @Operation(summary = "分页查询")
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
    @Operation(summary = "查询全部")
    @PostMapping("/queryAll")
    public Result<List<SysUser>> queryAll() {
        return Result.success(sysUserService.queryAllUser(Wrappers.emptyWrapper()));
    }

    /**
     * 根据角色id分页查询用户列表
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @Operation(summary = "根据角色id分页查询用户列表")
    @PostMapping("/userRoleList")
    public Result<PageResult<SysUserRoleResponse>> queryUserRolePage(@RequestBody UserRoleRequest userRoleRequest) {
        return Result.success(sysUserRoleService.queryPage(userRoleRequest));
    }

    /**
     * 根据部门id查询用户列表
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @Operation(summary = "根据部门id查询用户列表")
    @PostMapping("/userListByDept")
    public Result<List<SysUserResponse>> userListByDept(@RequestBody UserDeptRequest userDeptRequest) {
        return Result.success(sysUserDeptService.userListByDept(userDeptRequest));
    }

    /**
     * 根据用户id获取部门和角色信息
     *
     * @date 2021年4月24日星期六
     * @author zac
     */
    @Operation(summary = "根据用户id获取部门和角色信息")
    @PostMapping("/deptAndRoleInfo")
    public Result<SysUserDeptAndRoleInfo> deptAndRoleInfo(@RequestBody UserRequest userRequest) {
        return Result.success(sysUserService.deptAndRoleInfo(userRequest));
    }

}