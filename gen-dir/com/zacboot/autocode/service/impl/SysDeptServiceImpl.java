package com.zacboot.autocode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.zacboot.autocode.base.SysBaseServiceImpl;
import com.zacboot.autocode.dao.SysDeptDao;
import com.zacboot.autocode.dto.SysDept;
import com.zacboot.autocode.mapper.SysDeptMapper;
import com.zacboot.autocode.service.SysDeptService;
import com.zacboot.autocode.tablemodel.SysDept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2022年12月10日星期六
 * @author zac
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends SysBaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;

    public Integer add(SysDept sysDept) {
        return sysDeptDao.add(sysDept);
    }

    public Integer del(SysDept sysDept) {
        Assert.isTrue(BeanUtil.isEmpty(sysDept),"不能全部属性为空，会删除全表数据");
        return sysDeptDao.del(sysDept);
    }

    public Integer update(SysDept sysDept) {
        return sysDeptDao.update(sysDept);
    }

    public PageResult<SysDept> queryPage(SysDept sysDept) {
        PageResult<SysDept> pageResult = new PageResult<>();
        pageResult.setDataList(sysDeptDao.queryPage(sysDept,new Page(sysDept.getPageNumber(),sysDept.getPageSize())));
        pageResult.setTotal(sysDeptDao.queryPageCount(sysDept).intValue());
        return pageResult;
    }
}