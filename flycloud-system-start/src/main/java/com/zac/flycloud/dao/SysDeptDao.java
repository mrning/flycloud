package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysDept;
import com.zac.flycloud.bean.vos.DeptRequestVO;

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

    List<SysDept> queryPage(DeptRequestVO deptRequestVO, Page page);

    Long queryPageCount(DeptRequestVO deptRequestVO);

    List<SysDept> queryAll();
}