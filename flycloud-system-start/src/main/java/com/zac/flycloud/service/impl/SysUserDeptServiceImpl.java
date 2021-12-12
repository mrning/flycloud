package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.service.impl.SysBaseServiceImpl;
import com.zac.flycloud.dao.SysUserDeptDao;
import com.zac.flycloud.bean.dto.SysUserDeptDTO;
import com.zac.flycloud.dao.mapper.SysUserDeptMapper;
import com.zac.flycloud.service.SysUserDeptService;
import com.zac.flycloud.bean.tablemodel.SysUserDept;
import com.zac.flycloud.bean.vos.UserDeptRequestVO;
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

    public PageResult<SysUserDeptDTO> queryPage(UserDeptRequestVO userDeptRequestVO) {
        PageResult<SysUserDeptDTO> pageResult = new PageResult<>();
        pageResult.setDataList(sysUserDeptDao.queryPage(userDeptRequestVO,new Page(userDeptRequestVO.getPageNumber(),userDeptRequestVO.getPageSize())));
        pageResult.setTotal(sysUserDeptDao.queryPageCount(userDeptRequestVO).intValue());
        return pageResult;
    }
}