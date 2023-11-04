package com.zac.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zac.admin.beans.vos.request.SysDictPageRequest;
import com.zac.admin.dao.SysDictDao;
import com.zac.admin.mapper.SysDictMapper;
import com.zac.admin.service.SysDictService;
import com.zac.base.basebeans.PageResult;
import com.zac.base.bizentity.SysDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
@Slf4j
@Service
public class SysDictServiceImpl extends SysBaseServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Autowired
    private SysDictDao sysDictDao;

    @Override
    public Integer add(SysDict sysDict) {
        return sysDictDao.add(sysDict);
    }

    @Override
    public Integer del(SysDict sysDict) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysDict),"不能全部属性为空，会删除全表数据");
        return sysDictDao.del(sysDict);
    }

    @Override
    public Integer update(SysDict sysDict) {
        return sysDictDao.update(sysDict);
    }

    @Override
    public PageResult<SysDict> queryPage(SysDictPageRequest pageRequest) {
        PageResult<SysDict> pageResult = new PageResult<>();
        SysDict sysDict = pageRequest.converToDo();
        Page<SysDict> page = sysDictDao.queryPage(sysDict,new Page<>(pageRequest.getPage(),pageRequest.getPageSize()));
        pageResult.setDataList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public List<SysDict> queryAll() {
        return sysDictDao.queryAll();
    }

    @Override
    public List<SysDict> queryByParentUuid(String parentCode) {
        return sysDictDao.queryByParentUuid(parentCode);
    }
}