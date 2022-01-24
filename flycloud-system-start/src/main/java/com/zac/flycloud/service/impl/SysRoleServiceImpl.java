package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.dto.SysRole;
import com.zac.flycloud.bean.vos.RoleRequestVO;
import com.zac.flycloud.dao.SysRoleDao;
import com.zac.flycloud.dao.mapper.SysRoleMapper;
import com.zac.flycloud.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

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

    public Integer add(SysRole sysRole) {
        return sysRoleDao.add(sysRole);
    }

    public Integer del(SysRole sysRole) {
        Assert.isTrue(BeanUtil.isEmpty(sysRole),"不能全部属性为空，会删除全表数据");
        return sysRoleDao.del(sysRole);
    }

    public Integer update(SysRole sysRole) {
        return sysRoleDao.update(sysRole);
    }

    public PageResult<SysRole> queryPage(RoleRequestVO roleRequestVO) {
        PageResult<SysRole> pageResult = new PageResult<>();
        pageResult.setDataList(sysRoleDao.queryPage(roleRequestVO,new Page(roleRequestVO.getPageNumber(),roleRequestVO.getPageSize())));
        pageResult.setTotal(sysRoleDao.queryPageCount(roleRequestVO).intValue());
        return pageResult;
    }

    @Override
    public List<SysRole> queryAll() {
        return sysRoleDao.queryAll();
    }
}