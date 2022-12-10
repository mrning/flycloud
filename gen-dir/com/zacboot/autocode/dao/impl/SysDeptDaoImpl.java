package com.zacboot.autocode.dao.impl;

import cn.hutool.db.Page;
import com.zacboot.autocode.dao.SysDeptDao;
import com.zacboot.autocode.dto.SysDept;
import com.zacboot.autocode.dto.example.SysDeptExample;
import com.zacboot.autocode.mapper.SysDeptMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

/**
 * AutoCreateFile
 * @date 2022年12月10日星期六
 * @author zac
 */
@Repository
@Slf4j
public class SysDeptDaoImpl implements SysDeptDao {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    public Integer add(SysDept sysDept) {
        return sysDeptMapper.insertSelective(sysDept);
    }

    public Integer del(SysDept sysDept) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        buildExample(sysDeptExample)
        return sysDeptMapper.deleteByExample(sysDeptExample);
    }

    public Integer update(SysDept sysDept) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        return sysDeptMapper.updateByExampleSelective(sysDept,sysDeptExample);
    }

    public List<SysDept> queryPage(SysDept sysDept, Page page) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        return sysDeptMapper.selectByExampleWithRowbounds(sysDeptExample,new RowBounds(page.getPageNumber(),page.getPageSize()));
    }

    public Long queryPageCount(SysDept sysDept) {
        SysDeptExample sysDeptExample = new SysDeptExample();
        return sysDeptMapper.countByExample(sysDeptExample);
    }
}