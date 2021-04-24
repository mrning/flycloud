package com.zac.flycloud.dao.impl;

import com.zac.flycloud.dao.SysLogDao;
import com.zac.flycloud.dto.SysLogDTO;
import com.zac.flycloud.dto.example.SysLogDTOExample;
import com.zac.flycloud.mapper.SysLogDTOMapper;
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
public class SysLogDaoImpl implements SysLogDao {
    @Autowired
    private SysLogDTOMapper sysLogMapper;

    public Integer add(SysLogDTO sysLogDTO) {
        return sysLogMapper.insertSelective(sysLogDTO);
    }

    public Integer del(SysLogDTO sysLogDTO) {
        SysLogDTOExample sysLogDTOExample = new SysLogDTOExample();
        return sysLogMapper.deleteByExample(sysLogDTOExample);
    }

    public Integer update(SysLogDTO sysLogDTO) {
        SysLogDTOExample sysLogDTOExample = new SysLogDTOExample();
        return sysLogMapper.updateByExampleSelective(sysLogDTO,sysLogDTOExample);
    }

    public List<SysLogDTO> queryPage(SysLogDTO sysLogDTO, cn.hutool.db.Page page) {
        SysLogDTOExample sysLogDTOExample = new SysLogDTOExample();
        return sysLogMapper.selectByExampleWithRowbounds(sysLogDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysLogDTO sysLogDTO) {
        SysLogDTOExample sysLogDTOExample = new SysLogDTOExample();
        return sysLogMapper.countByExample(sysLogDTOExample);
    }
}