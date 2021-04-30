package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysUserDao;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.dto.example.SysUserDTOExample;
import com.zac.flycloud.mapper.SysUserDTOMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@Repository
@Slf4j
public class SysUserDaoImpl implements SysUserDao {
    @Autowired
    private SysUserDTOMapper sysUserMapper;

    public Integer add(SysUserDTO sysUserDTO) {
        return sysUserMapper.insertSelective(sysUserDTO);
    }

    public Integer del(SysUserDTO sysUserDTO) {
        SysUserDTOExample sysUserDTOExample = new SysUserDTOExample();
        return sysUserMapper.deleteByExample(sysUserDTOExample);
    }

    public Integer update(SysUserDTO sysUserDTO) {
        SysUserDTOExample sysUserDTOExample = new SysUserDTOExample();
        return sysUserMapper.updateByExampleSelective(sysUserDTO,sysUserDTOExample);
    }

    public List<SysUserDTO> queryPage(SysUserDTO sysUserDTO, Page page) {
        SysUserDTOExample sysUserDTOExample = new SysUserDTOExample();
        return sysUserMapper.selectByExampleWithRowbounds(sysUserDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysUserDTO sysUserDTO) {
        SysUserDTOExample sysUserDTOExample = new SysUserDTOExample();
        return sysUserMapper.countByExample(sysUserDTOExample);
    }
}