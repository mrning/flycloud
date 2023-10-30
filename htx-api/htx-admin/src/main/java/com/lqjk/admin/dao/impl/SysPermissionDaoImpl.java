package com.lqjk.admin.dao.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lqjk.admin.beans.example.SysPermissionExample;
import com.lqjk.admin.beans.vos.request.PermissionRequest;
import com.lqjk.admin.dao.SysPermissionDao;
import com.lqjk.admin.mapper.SysPermissionMapper;
import com.lqjk.base.bizentity.SysPermission;
import com.lqjk.base.utils.SysUtil;
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
 *
 * @author zac
 * @date 2022年12月11日星期日
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
        return sysPermissionMapper.deleteByExample(buildExample(sysPermission));
    }

    public Integer update(SysPermission sysPermission) {
        sysPermission.setUpdateTime(LocalDateTime.now());
        sysPermission.setUpdateUser(SysUtil.getCurrentUser().getNickname());
        return sysPermissionMapper.updateByExampleSelective(sysPermission, buildExample(sysPermission));
    }

    public List<SysPermission> queryPage(PermissionRequest permissionRequest, Page page) {
        return sysPermissionMapper.selectByExampleWithRowbounds(buildExample(SysPermission.convertByRequest(permissionRequest)), new RowBounds(page.getPageNumber(), page.getPageSize()));
    }

    public Long queryPageCount(PermissionRequest permissionRequest) {
        return sysPermissionMapper.countByExample(buildExample(SysPermission.convertByRequest(permissionRequest)));
    }

    public SysPermissionExample buildExample(SysPermission sysPermission) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        SysPermissionExample.Criteria criteria = sysPermissionExample.createCriteria();
        if (StringUtils.isNotBlank(sysPermission.getUuid())) {
            criteria.andUuidEqualTo(sysPermission.getUuid());
        }
        if (StringUtils.isNotBlank(sysPermission.getName())) {
            criteria.andNameLike("%" + sysPermission.getName() + "%");
        }
        criteria.andDeletedEqualTo(Boolean.FALSE);
        return sysPermissionExample;
    }

    @Override
    public Integer getMaxSortNo(String parentUuid) {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        SysPermissionExample.Criteria criteria = sysPermissionExample.createCriteria();
        if (StringUtils.isNotBlank(parentUuid)) {
            criteria.andParentUuidEqualTo(parentUuid);
        } else {
            criteria.andParentUuidIsNull();
        }
        // 获取相同父级uuid下排序值最大的对象
        List<SysPermission> sysPermission = sysPermissionMapper.selectByExample(sysPermissionExample);
        if (!CollectionUtils.isEmpty(sysPermission)) {
            return sysPermission.stream().max(Comparator.comparing(SysPermission::getSortNo)).get().getSortNo();
        }
        return 0;
    }

    @Override
    public SysPermission getByUuid(String uuid) {
        return sysPermissionMapper.selectOne(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getUuid, uuid));
    }
}