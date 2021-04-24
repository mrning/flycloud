package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.zac.flycloud.dao.SysUserDao;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    public Integer add(SysUserDTO sysUserDTO) {
        return sysUserDao.add(sysUserDTO);
    }

    public Integer del(SysUserDTO sysUserDTO) {
        Assert.isTrue(BeanUtil.isEmpty(sysUserDTO),"不能全部属性为空，会删除全表数据");
        return sysUserDao.del(sysUserDTO);
    }

    public Integer update(SysUserDTO sysUserDTO) {
        return sysUserDao.update(sysUserDTO);
    }

    public PageResult<SysUserDTO> queryPage(SysUserDTO sysUserDTO) {
        PageResult<SysUserDTO> pageResult = new PageResult<>();
        pageResult.addAll(sysUserDao.queryPage(sysUserDTO,new Page(sysUserDTO.getPageNumber(),sysUserDTO.getPageSize())));
        pageResult.setTotal(sysUserDao.queryPageCount(sysUserDTO).intValue());
        return pageResult;
    }
}