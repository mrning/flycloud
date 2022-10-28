package com.zacboot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zacboot.admin.beans.entity.SysUserRole;
import com.zacboot.admin.beans.vos.request.UserRoleRequest;
import com.zacboot.admin.beans.vos.response.SysUserRoleResponse;
import com.zacboot.admin.dao.SysRoleDao;
import com.zacboot.admin.dao.SysUserDao;
import com.zacboot.admin.dao.SysUserRoleDao;
import com.zacboot.admin.dao.mapper.SysUserRoleMapper;
import com.zacboot.admin.service.SysUserRoleService;
import com.zacboot.common.base.basebeans.PageResult;
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
        Assert.isTrue(BeanUtil.isEmpty(sysUserRole),"不能全部属性为空，会删除全表数据");
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
        pageResult.setTotal(sysUserRoleDao.queryPageCount(userRoleRequest).intValue());
        return pageResult;
    }

    @Override
    public List<SysUserRole> queryRolesByUserUuid(String userUuid) {
        return sysUserRoleDao.queryUserRoles(userUuid);
    }
}