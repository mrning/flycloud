package com.lqjk.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lqjk.admin.beans.vos.request.SysActivityPageRequest;
import com.lqjk.admin.dao.SysActivityDao;
import com.lqjk.admin.mapper.SysActivityMapper;
import com.lqjk.admin.service.SysActivityService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.bizentity.SysActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2023年10月30日星期一
 * @author zac
 */
@Slf4j
@Service
public class SysActivityServiceImpl extends SysBaseServiceImpl<SysActivityMapper, SysActivity> implements SysActivityService {
    @Autowired
    private SysActivityDao sysActivityDao;

    @Override
    public Integer add(SysActivity sysActivity) {
        return sysActivityDao.add(sysActivity);
    }

    @Override
    public Integer del(SysActivity sysActivity) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysActivity),"不能全部属性为空，会删除全表数据");
        return sysActivityDao.del(sysActivity);
    }

    @Override
    public Integer update(SysActivity sysActivity) {
        return sysActivityDao.update(sysActivity);
    }

    @Override
    public PageResult<SysActivity> queryPage(SysActivityPageRequest pageRequest) {
        PageResult<SysActivity> pageResult = new PageResult<>();
        Page<SysActivity> page = sysActivityDao.queryPage(pageRequest);
        pageResult.setDataList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }
}