package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.zac.flycloud.base.SysBaseServiceImpl;
import com.zac.flycloud.dao.SysUserDeptDao;
import com.zac.flycloud.dto.SysUserDeptDTO;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import com.zac.flycloud.service.SysUserDeptService;
import com.zac.flycloud.tablemodel.SysUserDept;
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
public class SysUserDeptServiceImpl extends SysBaseServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {
    @Autowired
    private SysUserDeptDao sysUserDeptDao;

    public Integer add(SysUserDeptDTO sysUserDeptDTO) {
        return sysUserDeptDao.add(sysUserDeptDTO);
    }

    public Integer del(SysUserDeptDTO sysUserDeptDTO) {
        Assert.isTrue(BeanUtil.isEmpty(sysUserDeptDTO),"不能全部属性为空，会删除全表数据");
        return sysUserDeptDao.del(sysUserDeptDTO);
    }

    public Integer update(SysUserDeptDTO sysUserDeptDTO) {
        return sysUserDeptDao.update(sysUserDeptDTO);
    }

    public PageResult<SysUserDeptDTO> queryPage(SysUserDeptDTO sysUserDeptDTO) {
        PageResult<SysUserDeptDTO> pageResult = new PageResult<>();
        pageResult.addAll(sysUserDeptDao.queryPage(sysUserDeptDTO,new Page(sysUserDeptDTO.getPageNumber(),sysUserDeptDTO.getPageSize())));
        pageResult.setTotal(sysUserDeptDao.queryPageCount(sysUserDeptDTO).intValue());
        return pageResult;
    }
}