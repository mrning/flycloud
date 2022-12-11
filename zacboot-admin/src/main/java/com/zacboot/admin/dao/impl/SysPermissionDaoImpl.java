package com.zacboot.admin.dao.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zacboot.admin.beans.entity.SysPermission;
import com.zacboot.admin.beans.example.SysPermissionExample;
import com.zacboot.admin.beans.vos.request.PermissionRequest;
import com.zacboot.admin.dao.SysPermissionDao;
import com.zacboot.admin.mapper.SysPermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2022年12月11日星期日
 * @author zac
 */
@Repository
@Slf4j
public class SysPermissionDaoImpl implements SysPermissionDao {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    public Integer add(SysPermission sysPermission) {
        return sysPermissionMapper.insertSelective(sysPermission);
    }

    public Integer del(SysPermission sysPermission) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        buildExample(sysPermission,sysPermissionExample);
        return sysPermissionMapper.deleteByExample(sysPermissionExample);
    }

    public Integer update(SysPermission sysPermission) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        buildExample(sysPermission,sysPermissionExample);
        return sysPermissionMapper.updateByExampleSelective(sysPermission,sysPermissionExample);
    }

    public List<SysPermission> queryPage(PermissionRequest permissionRequest, Page page) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        SysPermission sysPermission = new SysPermission();
        BeanUtil.copyProperties(permissionRequest,sysPermission);
        buildExample(sysPermission,sysPermissionExample);
        return sysPermissionMapper.selectByExampleWithRowbounds(sysPermissionExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(PermissionRequest permissionRequest) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        SysPermission sysPermission = new SysPermission();
        BeanUtil.copyProperties(permissionRequest,sysPermission);
        buildExample(sysPermission,sysPermissionExample);
        return sysPermissionMapper.countByExample(sysPermissionExample);
    }

    public SysPermissionExample buildExample(SysPermission sysPermission, SysPermissionExample sysPermissionExample) {
        SysPermissionExample.Criteria criteria = sysPermissionExample.createCriteria();
        return sysPermissionExample;
    }
}