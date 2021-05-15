package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.zac.flycloud.base.SysBaseServiceImpl;
import com.zac.flycloud.dao.SysRoleDao;
import com.zac.flycloud.dto.SysRoleDTO;
import com.zac.flycloud.mapper.SysRoleMapper;
import com.zac.flycloud.service.SysRoleService;
import com.zac.flycloud.tablemodel.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends SysBaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    public Integer add(SysRoleDTO sysRoleDTO) {
        return sysRoleDao.add(sysRoleDTO);
    }

    public Integer del(SysRoleDTO sysRoleDTO) {
        Assert.isTrue(BeanUtil.isEmpty(sysRoleDTO),"不能全部属性为空，会删除全表数据");
        return sysRoleDao.del(sysRoleDTO);
    }

    public Integer update(SysRoleDTO sysRoleDTO) {
        return sysRoleDao.update(sysRoleDTO);
    }

    public PageResult<SysRoleDTO> queryPage(SysRoleDTO sysRoleDTO) {
        PageResult<SysRoleDTO> pageResult = new PageResult<>();
        pageResult.addAll(sysRoleDao.queryPage(sysRoleDTO,new Page(sysRoleDTO.getPageNumber(),sysRoleDTO.getPageSize())));
        pageResult.setTotal(sysRoleDao.queryPageCount(sysRoleDTO).intValue());
        return pageResult;
    }
}