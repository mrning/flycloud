package com.zac.flycloud.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.flycloud.admin.beans.entity.SysPermission;
import com.zac.flycloud.admin.service.SysPermissionService;
import com.zac.flycloud.common.basebeans.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Api(tags = "权限相关")
@RestController
@RequestMapping("/admin/permission")
@Slf4j
public class AdminPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 查询用户拥有的菜单权限
     *
     * @return
     */
    @ApiOperation("获取用户权限")
    @GetMapping(value = "/getPermissionList")
    public Result<?> getPermissionList() {
        Result<JSONObject> result = new Result<JSONObject>();
        try {
            List<SysPermission> sysPermissions = sysPermissionService.list();
            //至少添加首页路由
            if (!sysPermissionService.hasIndexPage(sysPermissions)) {
                SysPermission indexMenu = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getUuid, "homepage")).get(0);
                sysPermissions.add(0, indexMenu);
            }
            JSONObject json = new JSONObject();
            JSONArray menuArray = new JSONArray();
            sysPermissions.sort(Comparator.comparingInt(SysPermission::getSortNo));
            sysPermissionService.getPermissionJsonArray(menuArray, sysPermissions, null);
            //路由菜单
            json.put("menu", menuArray);
            json.put("auth", sysPermissionService.getAuthJsonArray(sysPermissions));
            json.put("allAuth", sysPermissionService.getAllAuthJsonArray(sysPermissions));
            result.setResult(json);
            result.setMessage("查询成功");
        } catch (Exception e) {
            result.error500("查询失败:" + e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }
}
