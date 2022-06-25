package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.dto.SysUserRole;
import com.zac.flycloud.bean.tablemodel.SysUserRoleDTO;
import com.zac.flycloud.bean.vos.request.UserRoleRequest;
import com.zac.flycloud.bean.vos.response.SysUserRoleResponse;
import com.zac.flycloud.dao.SysRoleDao;
import com.zac.flycloud.dao.SysUserDao;
import com.zac.flycloud.dao.SysUserRoleDao;
import com.zac.flycloud.dao.mapper.SysUserRoleMapper;
import com.zac.flycloud.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public Integer add(SysUserRoleDTO sysUserRoleDTO) {
        return sysUserRoleDao.add(sysUserRoleDTO);
    }

    public Integer del(SysUserRoleDTO sysUserRoleDTO) {
        Assert.isTrue(BeanUtil.isEmpty(sysUserRoleDTO),"不能全部属性为空，会删除全表数据");
        return sysUserRoleDao.del(sysUserRoleDTO);
    }

    public Integer update(SysUserRoleDTO sysUserRoleDTO) {
        return sysUserRoleDao.update(sysUserRoleDTO);
    }

    public PageResult<SysUserRoleResponse> queryPage(UserRoleRequest userRoleRequest) {
        PageResult<SysUserRoleResponse> pageResult = new PageResult<>();
        pageResult.setDataList(sysUserRoleDao.queryPage(userRoleRequest,new Page(userRoleRequest.getPageNumber(), userRoleRequest.getPageSize()))
                .stream().map(sysUserRoleDTO -> {
                    sysUserDao.
                }).collect(Collectors.toList()));
        pageResult.setTotal(sysUserRoleDao.queryPageCount(userRoleRequest).intValue());
        return pageResult;
    }
}