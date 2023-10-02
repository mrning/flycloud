package com.lqjk.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqjk.admin.entity.SysPermission;
import com.lqjk.admin.beans.vos.request.PermissionAddRequest;
import com.lqjk.admin.beans.vos.request.PermissionRequest;
import com.lqjk.admin.beans.vos.request.PermissionUpdateRequest;
import com.lqjk.admin.dao.SysPermissionDao;
import com.lqjk.admin.mapper.SysPermissionMapper;
import com.lqjk.admin.service.SysPermissionService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.lqjk.base.constants.CommonConstant.MENU_TYPE_0;

@Service
public class SysPermissionServiceImpl extends SysBaseServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Override
    public Integer add(PermissionAddRequest permissionAddRequest) {
        SysPermission sysPermission = SysPermission.convertByRequest(permissionAddRequest);
        sysPermission.setUuid(UUID.randomUUID().toString(true));

        if (null == permissionAddRequest.getSortNo()){
            // 获取相同父级uuid下排序值最大的对象
            sysPermission.setSortNo(sysPermissionDao.getMaxSortNo(permissionAddRequest.getParentUuid())+1);
        }
        handleData(sysPermission);
        return sysPermissionDao.add(sysPermission);
    }

    private void handleData(SysPermission sysPermission) {
        // 如果是按钮权限的话
        if (StringUtils.isBlank(sysPermission.getUrl()) && CommonConstant.MENU_TYPE_2.equals(sysPermission.getMenuType())){
            SysPermission parent = sysPermissionDao.getByUuid(sysPermission.getParentUuid());
            sysPermission.setUrl(parent.getComponent());
        }
        if (StringUtils.isBlank(sysPermission.getComponent()) && sysPermission.getMenuType() < 2){
            sysPermission.setComponent(sysPermission.getUrl());
        }
    }

    @Override
    public Integer del(SysPermission sysPermission) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysPermission),"不能全部属性为空，会删除全表数据");
        return sysPermissionDao.del(sysPermission);
    }

    @Override
    public Integer update(PermissionUpdateRequest permissionUpdateRequest) {
        SysPermission sysPermission = SysPermission.convertByRequest(permissionUpdateRequest);
        // 如果是按钮权限的话
        handleData(sysPermission);
        return sysPermissionDao.update(sysPermission);
    }

    @Override
    public PageResult<SysPermission> queryPage(PermissionRequest permissionRequest) {
        PageResult<SysPermission> pageResult = new PageResult<>();
        pageResult.setDataList(sysPermissionDao.queryPage(permissionRequest,new Page(permissionRequest.getPageNumber(),permissionRequest.getPageSize())));
        pageResult.setTotal(sysPermissionDao.queryPageCount(permissionRequest));
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
                json.put("menuType", permission.getMenuType());
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
            if (permission.getMenuType().equals(MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
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
        json.put("uuid", permission.getUuid());
        json.put("name", permission.getName());
        json.put("component", permission.getComponent());
        json.put("menuType", permission.getMenuType());
        json.put("url", permission.getUrl());
        json.put("path", permission.getUrl());

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

}
