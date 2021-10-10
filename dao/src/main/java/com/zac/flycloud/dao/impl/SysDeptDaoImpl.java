package com.zac.flycloud.dao.impl;

import cn.hutool.db.Page;
import com.zac.flycloud.dao.SysDeptDao;
import com.zac.flycloud.dto.SysDeptDTO;
import com.zac.flycloud.dto.example.SysDeptDTOExample;
import com.zac.flycloud.mapper.SysDeptDTOMapper;
import java.util.List;

import com.zac.flycloud.vos.DeptRequestVO;
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
public class SysDeptDaoImpl implements SysDeptDao {
    @Autowired
    private SysDeptDTOMapper sysDeptMapper;

    public Integer add(SysDeptDTO sysDeptDTO) {
        return sysDeptMapper.insertSelective(sysDeptDTO);
    }

    public Integer del(SysDeptDTO sysDeptDTO) {
        SysDeptDTOExample sysDeptDTOExample = new SysDeptDTOExample();
        return sysDeptMapper.deleteByExample(sysDeptDTOExample);
    }

    public Integer update(SysDeptDTO sysDeptDTO) {
        SysDeptDTOExample sysDeptDTOExample = new SysDeptDTOExample();
        return sysDeptMapper.updateByExampleSelective(sysDeptDTO,sysDeptDTOExample);
    }

    public List<SysDeptDTO> queryPage(DeptRequestVO deptRequestVO, Page page) {
        SysDeptDTOExample sysDeptDTOExample = new SysDeptDTOExample();
        return sysDeptMapper.selectByExampleWithRowbounds(sysDeptDTOExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(DeptRequestVO deptRequestVO) {
        SysDeptDTOExample sysDeptDTOExample = new SysDeptDTOExample();
        return sysDeptMapper.countByExample(sysDeptDTOExample);
    }
}