package com.zac.flycloud.admin.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.admin.beans.entity.SysDept;
import com.zac.flycloud.admin.beans.vos.request.DeptRequest;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysDeptDao {
    Integer add(SysDept sysDept);

    Integer del(SysDept sysDept);

    Integer update(SysDept sysDept);

    List<SysDept> queryPage(DeptRequest deptRequest, Page page);

    Long queryPageCount(DeptRequest deptRequest);

    List<SysDept> queryAll();
}