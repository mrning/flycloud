package com.zac.flycloud.start.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.start.bean.constant.CommonConstant;
import com.zac.flycloud.start.bean.entity.SysPermission;
import com.zac.flycloud.start.dao.mapper.SysPermissionMapper;
import com.zac.flycloud.start.service.SysPermissionService;
import com.zac.flycloud.start.utils.MD5Util;
import com.zac.flycloud.start.utils.UrlIPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends SysBaseServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    /**
     * 获取权限JSON数组
     *
     * @param allList
     */
    @Override
    public JSONArray getAllAuthJsonArray(List<SysPermission> allList) {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = null;
        for (SysPermission permission : allList) {
            json = new JSONObject();
            json.put("action", permission.getPerms());
            json.put("status", permission.getStatus());
            json.put("type", permission.getPermsType());
            json.put("describe", permission.getName());
            jsonArray.add(json);
        }
        return jsonArray;
    }

    /**
     * 获取权限JSON数组
     *
     * @param metaList
     */
    @Override
    public JSONArray getAuthJsonArray(List<SysPermission> metaList) {
        JSONArray jsonArray = new JSONArray();
        for (SysPermission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            JSONObject json = null;
            if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2) && CommonConstant.STATUS_1.equals(permission.getStatus())) {
                json = new JSONObject();
                json.put("action", permission.getPerms());
                json.put("type", permission.getPermsType());
                json.put("describe", permission.getName());
                jsonArray.add(json);
            }
        }
        return jsonArray;
    }

    /**
     * 获取菜单JSON数组
     *
     * @param sysPermissions
     * @param parentJson
     */
    @Override
    public void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> sysPermissions, JSONObject parentJson) {
        for (SysPermission permission : sysPermissions) {
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
                    getPermissionJsonArray(jsonArray, sysPermissions, json);
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
                        getPermissionJsonArray(jsonArray, sysPermissions, json);
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
    @Override
    public JSONObject getPermissionJsonObject(SysPermission permission) {
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
    @Override
    public boolean hasIndexPage(List<SysPermission> metaList) {
        boolean hasIndexMenu = false;
        for (SysPermission sysPermission : metaList) {
            if ("homepage".equals(sysPermission.getUuid())) {
                hasIndexMenu = true;
                break;
            }
        }
        return hasIndexMenu;
    }
}
