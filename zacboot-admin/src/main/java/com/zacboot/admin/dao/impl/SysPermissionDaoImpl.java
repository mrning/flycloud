package com.zacboot.admin.dao.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.system.core.entity.admin.SysPermission;
import com.zacboot.admin.beans.example.SysPermissionExample;
import com.zacboot.admin.beans.vos.request.PermissionRequest;
import com.zacboot.admin.dao.SysPermissionDao;
import com.zacboot.admin.mapper.SysPermissionMapper;
import com.zacboot.admin.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
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
        sysPermission.setCreateTime(LocalDateTime.now());
        sysPermission.setCreateUser(SysUtil.getCurrentUser().getNickname());
        sysPermission.setDeleted(false);
        return sysPermissionMapper.insertSelective(sysPermission);
    }

    public Integer del(SysPermission sysPermission) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        buildExample(sysPermission,sysPermissionExample);
        return sysPermissionMapper.deleteByExample(sysPermissionExample);
    }

    public Integer update(SysPermission sysPermission) {
        sysPermission.setUpdateTime(LocalDateTime.now());
        sysPermission.setUpdateUser(SysUtil.getCurrentUser().getNickname());
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
        if (StringUtils.isNotBlank(sysPermission.getUuid())){
            criteria.andUuidEqualTo(sysPermission.getUuid());
        }
        return sysPermissionExample;
    }

    @Override
    public Integer getMaxSortNo(String parentUuid) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        SysPermissionExample.Criteria criteria = sysPermissionExample.createCriteria();
        if (StringUtils.isNotBlank(parentUuid)){
            criteria.andParentUuidEqualTo(parentUuid);
        }else{
            criteria.andParentUuidIsNull();
        }
        // 获取相同父级uuid下排序值最大的对象
        List<SysPermission> sysPermission = sysPermissionMapper.selectByExample(sysPermissionExample);
        if (!CollectionUtils.isEmpty(sysPermission)){
            return sysPermission.stream().max(Comparator.comparing(SysPermission::getSortNo)).get().getSortNo();
        }
        return 0;
    }

    @Override
    public SysPermission getByUuid(String uuid) {
        return sysPermissionMapper.selectOne(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getUuid,uuid));
    }
}