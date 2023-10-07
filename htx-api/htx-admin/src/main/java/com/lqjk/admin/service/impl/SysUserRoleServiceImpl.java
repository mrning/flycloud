package com.lqjk.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.lqjk.base.bizentity.SysUserRole;
import com.lqjk.admin.beans.vos.request.UserRoleRequest;
import com.lqjk.admin.beans.vos.response.SysUserRoleResponse;
import com.lqjk.admin.dao.SysRoleDao;
import com.lqjk.admin.dao.SysUserDao;
import com.lqjk.admin.dao.SysUserRoleDao;
import com.lqjk.admin.mapper.SysUserRoleMapper;
import com.lqjk.admin.service.SysUserRoleService;
import com.lqjk.base.basebeans.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends SysBaseServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    public Integer add(SysUserRole sysUserRole) {
        return sysUserRoleDao.add(sysUserRole);
    }

    public Integer del(SysUserRole sysUserRole) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysUserRole),"不能全部属性为空，会删除全表数据");
        return sysUserRoleDao.del(sysUserRole);
    }

    public Integer update(SysUserRole sysUserRole) {
        return sysUserRoleDao.update(sysUserRole);
    }

    public PageResult<SysUserRoleResponse> queryPage(UserRoleRequest userRoleRequest) {
        PageResult<SysUserRoleResponse> pageResult = new PageResult<>();
        pageResult.setDataList(sysUserRoleDao.queryPage(userRoleRequest,new Page(userRoleRequest.getPageNumber(), userRoleRequest.getPageSize()))
                .stream().map(sysUserRoleDTO -> new SysUserRoleResponse(sysUserDao.queryByUuid(sysUserRoleDTO.getUserUuid()),
                        sysRoleDao.queryByUuid(sysUserRoleDTO.getRoleUuid()))).collect(Collectors.toList()));
        pageResult.setTotal(sysUserRoleDao.queryPageCount(userRoleRequest));
        return pageResult;
    }

    @Override
    public List<SysUserRole> queryRolesByUserUuid(String userUuid) {
        return sysUserRoleDao.queryUserRoles(userUuid);
    }

    @Override
    public Integer updateByUserUuid(String userUuid, List<String> roleUuids) {
        sysUserRoleDao.delByUserUuid(userUuid);
        roleUuids.forEach(roleUuid -> {
            SysUserRole sysUserRole = new SysUserRole(userUuid, roleUuid);
            sysUserRoleDao.add(sysUserRole);
        });

        return roleUuids.size();
    }

    @Override
    public Integer delByUserUuid(String userUuid) {
        return sysUserRoleDao.delByUserUuid(userUuid);
    }
}