package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysUserDeptDao;
import com.zac.flycloud.bean.dto.SysUserDeptDTO;
import com.zac.flycloud.bean.dto.example.SysUserDeptDTOExample;
import com.zac.flycloud.dao.mapper.SysUserDeptDTOMapper;
import java.util.List;

import com.zac.flycloud.bean.vos.UserDeptRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Repository
@Slf4j
public class SysUserDeptDaoImpl implements SysUserDeptDao {
    @Autowired
    private SysUserDeptDTOMapper sysUserDeptMapper;

    public Integer add(SysUserDeptDTO sysUserDeptDTO) {
        return sysUserDeptMapper.insertSelective(sysUserDeptDTO);
    }

    public Integer del(SysUserDeptDTO sysUserDeptDTO) {
        SysUserDeptDTOExample sysUserDeptDTOExample = new SysUserDeptDTOExample();
        return sysUserDeptMapper.deleteByExample(sysUserDeptDTOExample);
    }

    public Integer update(SysUserDeptDTO sysUserDeptDTO) {
        SysUserDeptDTOExample sysUserDeptDTOExample = new SysUserDeptDTOExample();
        return sysUserDeptMapper.updateByExampleSelective(sysUserDeptDTO,sysUserDeptDTOExample);
    }

    public List<SysUserDeptDTO> queryPage(UserDeptRequestVO userDeptRequestVO, Page page) {
        SysUserDeptDTOExample sysUserDeptDTOExample = new SysUserDeptDTOExample();
        return sysUserDeptMapper.selectByExampleWithRowbounds(sysUserDeptDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(UserDeptRequestVO userDeptRequestVO) {
        SysUserDeptDTOExample sysUserDeptDTOExample = new SysUserDeptDTOExample();
        return sysUserDeptMapper.countByExample(sysUserDeptDTOExample);
    }
}