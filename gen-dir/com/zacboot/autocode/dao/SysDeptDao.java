package com.zacboot.autocode.dao;

import cn.hutool.db.Page;
import com.zacboot.autocode.dto.SysDept;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2022年12月10日星期六
 * @author zac
 */
public interface SysDeptDao {
    Integer add(SysDept sysDept);

    Integer del(SysDept sysDept);

    Integer update(SysDept sysDept);

    List<SysDept> queryPage(SysDept sysDept, Page page);

    Long queryPageCount(SysDept sysDept);
}