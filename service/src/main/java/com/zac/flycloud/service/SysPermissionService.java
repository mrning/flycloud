package com.zac.flycloud.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.base.SysBaseService;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.tablemodel.SysPermission;
import com.zac.flycloud.utils.MD5Util;
import com.zac.flycloud.utils.UrlIPUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public interface SysPermissionService extends SysBaseService<SysPermission> {

    void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> sysPermissions, JSONObject parentJson);

    JSONObject getPermissionJsonObject(SysPermission permission);

    boolean hasIndexPage(List<SysPermission> metaList);
}
