package com.lqjk.admin.service;

import com.lqjk.admin.beans.vos.request.SysDictPageRequest;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.admin.entity.SysDict;

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