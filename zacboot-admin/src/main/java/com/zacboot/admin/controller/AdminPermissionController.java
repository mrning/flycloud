package com.zacboot.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zac.system.core.entity.admin.SysPermission;
import com.zacboot.admin.beans.vos.request.PermissionAddRequest;
import com.zacboot.admin.beans.vos.request.PermissionRequest;
import com.zacboot.admin.beans.vos.request.PermissionUpdateRequest;
import com.zacboot.admin.service.SysPermissionService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Api(tags = "权限相关")
@RestController
@RequestMapping("/permission")
@Slf4j
public class AdminPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * AutoCreateFile add
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody PermissionAddRequest permissionAddRequest) {
        return Result.success(sysPermissionService.add(permissionAddRequest));
    }

    /**
     * AutoCreateFile del
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody SysPermission sysPermission) {
        return Result.success(sysPermissionService.del(sysPermission));
    }

    /**
     * AutoCreateFile update
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody PermissionUpdateRequest permissionUpdateRequest) {
        return Result.success(sysPermissionService.update(permissionUpdateRequest));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<SysPermission>> queryPage(@RequestBody PermissionRequest permissionRequest) {
        return Result.success(sysPermissionService.queryPage(permissionRequest));
    }

    /**
     * 查询用户拥有的菜单权限
     *
     * @return
     */
    @ApiOperation("获取用户权限")
    @GetMapping(value = "/getPermissionList")
    public Result<?> getPermissionList() {
        List<SysPermission> sysPermissions = sysPermissionService.list();
        JSONObject json = new JSONObject();
        JSONArray menuArray = new JSONArray();
        sysPermissions.sort(Comparator.comparingInt(SysPermission::getSortNo));
        sysPermissionService.getPermissionJsonArray(menuArray, sysPermissions, null);
        // 路由菜单
        json.put("menu", menuArray);
        // 权限
        json.put("auth", sysPermissionService.getAuthJsonArray(sysPermissions));
        return Result.success(json);
    }
}
