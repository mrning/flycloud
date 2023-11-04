package com.zac.admin.service;

import com.zac.admin.beans.vos.request.SysDictPageRequest;
import com.zac.base.basebeans.PageResult;
import com.zac.base.bizentity.SysDict;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
public interface SysDictService extends SysBaseService<SysDict> {
    Integer add(SysDict sysDict);

    Integer del(SysDict sysDict);

    Integer update(SysDict sysDict);

    PageResult<SysDict> queryPage(SysDictPageRequest pageRequest);

    List<SysDict> queryAll();

    List<SysDict> queryByParentUuid(String parentCode);
}