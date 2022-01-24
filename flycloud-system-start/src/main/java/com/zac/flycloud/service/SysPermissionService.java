package com.zac.flycloud.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.bean.dto.SysPermission;

import java.util.List;

public interface SysPermissionService extends SysBaseService<SysPermission> {

    JSONArray getAllAuthJsonArray(List<SysPermission> allList);

    JSONArray getAuthJsonArray(List<SysPermission> metaList);

    void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> sysPermissions, JSONObject parentJson);

    JSONObject getPermissionJsonObject(SysPermission permission);

    boolean hasIndexPage(List<SysPermission> metaList);
}
