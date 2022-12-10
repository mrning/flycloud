package com.zacboot.autocode.service;

import cn.hutool.db.PageResult;
import com.zacboot.autocode.base.SysBaseService;
import com.zacboot.autocode.dto.SysDept;
import com.zacboot.autocode.tablemodel.SysDept;

/**
 * AutoCreateFile
 * @date 2022年12月10日星期六
 * @author zac
 */
public interface SysDeptService extends SysBaseService<SysDept> {
    Integer add(SysDept sysDept);

    Integer del(SysDept sysDept);

    Integer update(SysDept sysDept);

    PageResult<SysDept> queryPage(SysDept sysDept);
}