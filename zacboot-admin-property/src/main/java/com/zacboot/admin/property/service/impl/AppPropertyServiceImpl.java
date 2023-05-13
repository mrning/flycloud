package com.zacboot.admin.property.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.admin.property.dao.AppPropertyDao;
import com.zacboot.admin.property.dto.request.AppPropertyPageRequest;
import com.zacboot.admin.property.mapper.AppPropertyMapper;
import com.zacboot.admin.property.service.AppPropertyService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.system.core.entity.administration.AppProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2023年4月26日星期三
 * @author zac
 */
@Slf4j
@Service
public class AppPropertyServiceImpl extends SysBaseServiceImpl<AppPropertyMapper, AppProperty> implements AppPropertyService {
    @Autowired
    private AppPropertyDao appPropertyDao;

    @Override
    public Integer add(AppProperty appProperty) {
        return appPropertyDao.add(appProperty);
    }

    @Override
    public Integer del(AppProperty appProperty) {
        Assert.isTrue(BeanUtil.isNotEmpty(appProperty),"不能全部属性为空，会删除全表数据");
        return appPropertyDao.del(appProperty);
    }

    @Override
    public Integer update(AppProperty appProperty) {
        return appPropertyDao.update(appProperty);
    }

    @Override
    public PageResult<AppProperty> queryPage(AppPropertyPageRequest pageRequest) {
        PageResult<AppProperty> pageResult = new PageResult<>();
        AppProperty appProperty = pageRequest.converToDo();
        Page<AppProperty> page = appPropertyDao.queryPage(appProperty,new Page<>(pageRequest.getPage(),pageRequest.getLimit()));
        pageResult.setDataList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }
}