package com.zac.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zac.base.bizentity.SysPermission;
import com.zac.admin.beans.vos.request.PermissionAddRequest;
import com.zac.admin.beans.vos.request.PermissionRequest;
import com.zac.admin.beans.vos.request.PermissionUpdateRequest;
import com.zac.base.basebeans.PageResult;

import java.util.List;

public interface SysPermissionService extends SysBaseService<SysPermission> {

    Integer add(PermissionAddRequest sysPermission);

    Integer del(SysPermission sysPermission);

    Integer update(PermissionUpdateRequest permissionUpdateRequest);

    PageResult<SysPermission> queryPage(PermissionRequest permissionRequest);

    JSONArray getAuthJsonArray(List<SysPermission> metaList);

    void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> sysPermissions, JSONObject parentJson);

    JSONObject getPermissionJsonObject(SysPermission permission);

}
