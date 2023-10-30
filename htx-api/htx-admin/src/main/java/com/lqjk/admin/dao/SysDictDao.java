package com.lqjk.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lqjk.base.bizentity.SysDict;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
public interface SysDictDao {
    Integer add(SysDict sysDict);

    Integer del(SysDict sysDict);

    Integer update(SysDict sysDict);

    Page<SysDict> queryPage(SysDict sysDict, Page<SysDict> page);

    Long queryPageCount(SysDict sysDict);

    List<SysDict> queryAll();

    List<SysDict> queryByParentUuid(String parentCode);
}