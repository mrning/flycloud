package com.zac.flycloud.restfulcontroller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.entity.tablemodel.SysPermission;
import com.zac.flycloud.sys.SysPermissionService;
import com.zac.flycloud.utils.MD5Util;
import com.zac.flycloud.utils.UrlIPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Api(tags = "权限相关")
@RestController
@RequestMapping("/api/permission")
@Slf4j
public class PermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 查询用户拥有的菜单权限
     *
     * @return
     */
    @ApiOperation("获取用户权限")
    @RequestMapping(value = "/getPermissionList", method = RequestMethod.GET)
    public DataResponseResult<?> getPermissionList() {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        try {
            List<SysPermission> sysPermissions = sysPermissionService.list();
            //至少添加首页路由
            if (!hasIndexPage(sysPermissions)) {
                SysPermission indexMenu = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getUuid, "homepage")).get(0);
                sysPermissions.add(0, indexMenu);
            }
            JSONObject json = new JSONObject();
            JSONArray menujsonArray = new JSONArray();
            sysPermissions.sort(Comparator.comparingInt(SysPermission::getSortNo));
            this.getPermissionJsonArray(menujsonArray, sysPermissions, null);
            //路由菜单
            json.put("menu", menujsonArray);
            result.setResult(json);
            result.setMessage("查询成功");
        } catch (Exception e) {
            result.error500("查询失败:" + e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取菜单JSON数组
     *
     * @param jsonArray
     * @param metaList
     * @param parentJson
     */
    private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> metaList, JSONObject parentJson) {
        for (SysPermission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            String parentId = permission.getParentId();
            JSONObject json = getPermissionJsonObject(permission);
            if (json == null) {
                continue;
            }
            // 如果父级菜单为空
            if (parentJson == null && StringUtils.isEmpty(parentId)) {
                jsonArray.add(json);
                if (!permission.isLeaf()) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && StringUtils.isNotEmpty(parentId) && parentId.equals(parentJson.getString("uuid"))) {
                // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
                    JSONObject metaJson = parentJson.getJSONObject("meta");
                    if (metaJson.containsKey("permissionList")) {
                        metaJson.getJSONArray("permissionList").add(json);
                    } else {
                        JSONArray permissionList = new JSONArray();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_1) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_0)) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJSONArray("children").add(json);
                    } else {
                        JSONArray children = new JSONArray();
                        children.add(json);
                        parentJson.put("children", children);
                    }

                    if (!permission.isLeaf()) {
                        getPermissionJsonArray(jsonArray, metaList, json);
                    }
                }
            }

        }
    }

    /**
     * 根据菜单配置生成路由json
     *
     * @param permission
     * @return
     */
    private JSONObject getPermissionJsonObject(SysPermission permission) {
        JSONObject json = new JSONObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
            return null;
        } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
            // 是否隐藏路由，默认都是显示的
            if (permission.isHidden()) {
                return null;
            }
            json.put("uuid", permission.getUuid());
            if (permission.isRoute()) {
                json.put("route", "1");// 表示生成路由
            } else {
                json.put("route", "0");// 表示不生成路由
            }
            // 判断url是否外链
            if (UrlIPUtils.isWWWHttpUrl(permission.getUrl())) {
                json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
            } else {
                json.put("path", permission.getUrl());
            }
            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (StringUtils.isNotBlank(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", UrlIPUtils.urlToRouteName(permission.getUrl()));
            }
            json.put("component", permission.getComponent());

            JSONObject meta = new JSONObject();
            // 由用户设置是否缓存页面 用布尔值
            meta.put("keepAlive", permission.isKeepAlive());
            // 外链菜单打开方式
            meta.put("internalOrExternal", permission.isInternalOrExternal());
            // 菜单名称
            meta.put("title", permission.getName());
            if (StringUtils.isEmpty(permission.getParentId())) {
                // 一级菜单跳转地址
                json.put("redirect", permission.getRedirect());
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            } else {
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            }
            // 如果是外链的话
            if (UrlIPUtils.isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }

        return json;
    }

    /**
     * 判断是否授权首页
     *
     * @param metaList
     * @return
     */
    public boolean hasIndexPage(List<SysPermission> metaList) {
        boolean hasIndexMenu = false;
        for (SysPermission sysPermission : metaList) {
            if ("首页".equals(sysPermission.getName())) {
                hasIndexMenu = true;
                break;
            }
        }
        return hasIndexMenu;
    }
}
