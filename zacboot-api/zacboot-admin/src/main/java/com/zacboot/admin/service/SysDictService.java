package com.zacboot.admin.service;

import com.zacboot.admin.beans.vos.request.SysDictPageRequest;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.core.entity.admin.SysDict;

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
}