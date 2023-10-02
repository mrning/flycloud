package com.lqjk.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqjk.admin.entity.SysPermission;
import com.lqjk.admin.beans.vos.request.PermissionAddRequest;
import com.lqjk.admin.beans.vos.request.PermissionRequest;
import com.lqjk.admin.beans.vos.request.PermissionUpdateRequest;
import com.lqjk.admin.service.SysPermissionService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.basebeans.Result;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Tag(name = "权限相关")
@RestController
@RequestMapping("/permission")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AdminPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * AutoCreateFile add
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody PermissionAddRequest permissionAddRequest) {
        return Result.success(sysPermissionService.add(permissionAddRequest));
    }

    /**
     * AutoCreateFile del
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysPermission sysPermission) {
        return Result.success(sysPermissionService.del(sysPermission));
    }

    /**
     * AutoCreateFile update
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody PermissionUpdateRequest permissionUpdateRequest) {
        return Result.success(sysPermissionService.update(permissionUpdateRequest));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2022年12月11日星期日
     * @author zac
     */
    @PostMapping("/queryPage")
    public Result<PageResult<SysPermission>> queryPage(@RequestBody PermissionRequest permissionRequest) {
        return Result.success(sysPermissionService.queryPage(permissionRequest));
    }

    /**
     * 查询用户拥有的菜单权限
     *
     * @return
     */
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
