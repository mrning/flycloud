package com.zacboot.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zacboot.admin.beans.entity.SysPermission;
import com.zacboot.admin.beans.vos.request.PermissionRequest;
import com.zacboot.common.base.basebeans.PageResult;

import java.util.List;

public interface SysPermissionService extends SysBaseService<SysPermission> {

    Integer add(SysPermission sysPermission);

    Integer del(SysPermission sysPermission);

    Integer update(SysPermission sysPermission);

    PageResult<SysPermission> queryPage(PermissionRequest permissionRequest);

    JSONArray getAuthJsonArray(List<SysPermission> metaList);

    void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> sysPermissions, JSONObject parentJson);

    JSONObject getPermissionJsonObject(SysPermission permission);

    boolean hasIndexPage(List<SysPermission> metaList);
}
