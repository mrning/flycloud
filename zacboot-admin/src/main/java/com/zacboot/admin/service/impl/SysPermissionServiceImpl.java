package com.zacboot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zacboot.admin.beans.entity.SysPermission;
import com.zacboot.admin.beans.vos.request.PermissionRequest;
import com.zacboot.admin.dao.SysPermissionDao;
import com.zacboot.admin.mapper.SysPermissionMapper;
import com.zacboot.admin.service.SysPermissionService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.constants.CommonConstant;
import com.zacboot.common.base.utils.MD5Util;
import com.zacboot.common.base.utils.UrlIPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends SysBaseServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Override
    public Integer add(SysPermission sysPermission) {
        return sysPermissionDao.add(sysPermission);
    }

    @Override
    public Integer del(SysPermission sysPermission) {
        Assert.isTrue(BeanUtil.isEmpty(sysPermission),"不能全部属性为空，会删除全表数据");
        return sysPermissionDao.del(sysPermission);
    }

    @Override
    public Integer update(SysPermission sysPermission) {
        return sysPermissionDao.update(sysPermission);
    }

    @Override
    public PageResult<SysPermission> queryPage(PermissionRequest permissionRequest) {
        PageResult<SysPermission> pageResult = new PageResult<>();
        pageResult.setDataList(sysPermissionDao.queryPage(permissionRequest,new Page(permissionRequest.getPageNumber(),permissionRequest.getPageSize())));
        pageResult.setTotal(sysPermissionDao.queryPageCount(permissionRequest).intValue());
        return pageResult;
    }

    /**
     * 获取权限JSON数组
     *
     * @param authList
     */
    @Override
    public JSONArray getAuthJsonArray(List<SysPermission> authList) {
        JSONArray jsonArray = new JSONArray();
        for (SysPermission permission : authList) {
            if (permission.getMenuType() != null && CommonConstant.MENU_TYPE_2.equals(permission.getMenuType())) {
                JSONObject json = new JSONObject();
                json.put("uuid", permission.getUuid());
                json.put("parentUuid", permission.getParentUuid());
                json.put("code", permission.getCode());
                json.put("url", permission.getUrl());
                json.put("name", permission.getName());
                json.put("type", permission.getMenuType());
                json.put("hidden", permission.getHidden());
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
            // 如果菜单类型为空或者未获取到菜单信息
            if (permission.getMenuType() == null || null == getPermissionJsonObject(permission)) {
                continue;
            }
            // 0=父级菜单 1=子菜单
            if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
                // 处理获取单个菜单信息
                JSONObject json = getPermissionJsonObject(permission);
                String parentUuid = permission.getParentUuid();
                // 如果父级菜单为空
                if (parentJson == null && StringUtils.isEmpty(parentUuid)) {
                    jsonArray.add(json);
                    getPermissionJsonArray(jsonArray, sysPermissions, json);
                } else if (parentJson != null && StringUtils.isNotEmpty(parentUuid) && parentUuid.equals(parentJson.getString("uuid"))) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJSONArray("children").add(json);
                    } else {
                        JSONArray children = new JSONArray();
                        children.add(json);
                        parentJson.put("children", children);
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
        // 是否隐藏路由，默认都是显示的
        if (permission.getHidden()) {
            return null;
        }
        json.put("uuid", permission.getUuid());
        // 判断url是否外链
        if (UrlIPUtils.isWWWHttpUrl(permission.getUrl())) {
            json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
        } else {
            json.put("path", permission.getUrl());
        }
        // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
        if (StringUtils.isNotBlank(permission.getComponent())) {
            json.put("name", permission.getName());
        } else {
            json.put("name", UrlIPUtils.urlToRouteName(permission.getUrl()));
        }
        json.put("component", permission.getComponent());

        JSONObject meta = new JSONObject();
        // 菜单名称
        meta.put("title", permission.getName());
        if (StringUtils.isEmpty(permission.getParentUuid())) {
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
        // 排序
        if (null != permission.getSortNo()) {
            meta.put("sort", permission.getSortNo());
        }
        json.put("meta", meta);

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
