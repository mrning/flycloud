package com.lqjk.admin.controller;

import com.lqjk.base.bizentity.SysRole;
import com.lqjk.admin.beans.vos.request.RoleAddRequest;
import com.lqjk.admin.beans.vos.request.RoleRequest;
import com.lqjk.admin.beans.vos.request.RoleUpdateRequest;
import com.lqjk.admin.beans.vos.response.RolePageResponse;
import com.lqjk.admin.service.SysRoleService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.basebeans.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AutoCreateFile 角色管理 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/sysRole")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AdminSysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * AutoCreateFile add
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "添加角色")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody RoleAddRequest addRequest) {
        return Result.success(sysRoleService.add(addRequest));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "删除角色")
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysRole sysRole) {
        return Result.success(sysRoleService.del(sysRole));
    }

    /**
     * AutoCreateFile update
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "修改角色")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        return Result.success(sysRoleService.update(roleUpdateRequest));
    }

    /**
     * AutoCreateFile queryPage
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<RolePageResponse>> queryPage(@RequestBody RoleRequest roleRequest) {
        return Result.success(sysRoleService.queryPage(roleRequest));
    }

    /**
     * 查询全部角色
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "查询全部")
    @PostMapping("/queryAll")
    public Result<List<SysRole>> queryAll() {
        return Result.success(sysRoleService.queryAll());
    }

    @Operation(summary = "根据用户uuid查询")
    @GetMapping("/queryByUserUuid")
    public Result<List<SysRole>> queryByUserUuid(@RequestParam String userUuid){
        return Result.success(sysRoleService.queryUserRoles(userUuid));
    }
}